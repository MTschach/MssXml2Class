package de.mss.xml2class;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Xml2Class {

   private static final String      CMD_TEMPLATES = "templates";
   private static final String      CMD_INPUT     = "input";
   private static final String      CMD_OUTPUT    = "output";

   private String                   nl           = System.getProperty("line.separator");

   private String            templatePath  = "templates";
   private String                   inputPath     = "params";
   private String            outputPath    = "gen";

   private Map<String, ClassHolder> classList     = new HashMap<>();
   private Map<String, String>      templateList  = new HashMap<>();


   private static final String[] SECTIONS = {"VARIABLES", "GETTER", "SETTER", "TO_STRING", "PRINT_METHODS"};

   public Xml2Class(String[] args) throws ParseException {
      init(args);
   }


   private void init(String[] args) throws ParseException {
      Options cmdArgs = new Options();

      Option t = new Option("t", CMD_TEMPLATES, true, "template path");
      t.setRequired(false);
      cmdArgs.addOption(t);

      Option o = new Option("o", CMD_OUTPUT, true, "output path");
      o.setRequired(false);
      cmdArgs.addOption(o);

      Option i = new Option("i", CMD_INPUT, true, "input path");
      i.setRequired(false);
      cmdArgs.addOption(i);

      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse(cmdArgs, args);

      if (cmd.hasOption(CMD_TEMPLATES))
         this.templatePath = cmd.getOptionValue(CMD_TEMPLATES);

      if (cmd.hasOption(CMD_OUTPUT))
         this.outputPath = cmd.getOptionValue(CMD_OUTPUT);

      if (cmd.hasOption(CMD_INPUT))
         this.inputPath = cmd.getOptionValue(CMD_INPUT);
   }


   public void doWork() throws ParserConfigurationException, SAXException, IOException {
      readXmlFiles(new File(this.inputPath));

      readTemplates(new File(this.templatePath));

      for (Entry<String, ClassHolder> entry : this.classList.entrySet()) {
         writeClass(entry.getValue());
      }
   }


   private void writeClass(ClassHolder clazz) throws IOException {
      for (Entry<String, String> entry : this.templateList.entrySet())
         writeClass(clazz, entry.getValue());

   }


   public void readXmlFiles(File file) throws ParserConfigurationException, SAXException, IOException {
      if (!file.exists())
         return;

      if (file.isDirectory()) {
         for (File f : file.listFiles(new XmlFileNameFilter())) {
            readXmlFiles(f);
         }
         return;
      }

      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);

      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("classes").item(0).getChildNodes();

      for (int temp = 0; temp < nList.getLength(); temp++ ) {
         Node nNode = nList.item(temp);

         if (nNode.getNodeType() == Node.ELEMENT_NODE) {

            Element eElement = (Element)nNode;

            ClassHolder cl = new ClassHolder();

            cl.setName(eElement.getAttribute("name"));
            cl.setPackageName(eElement.getAttribute("package"));
            cl.setImplementsFrom(eElement.getAttribute("implements"));
            cl.setExtendsFrom(eElement.getAttribute("extends"));
            cl.setVersion(eElement.getAttribute("version"));
            cl.setComment(eElement.getAttribute("comment"));

            cl.setConstants(getConstants(eElement.getChildNodes()));
            cl.setVariables(getVariables(eElement.getChildNodes()));
            cl.setSpecialMethods(getSpecialMethods(eElement.getChildNodes()));

            this.classList.put(cl.getPackageName() + "." + cl.getName(), cl);
         }
      }
   }


   private void readTemplates(File file) throws IOException {
      if (!file.exists())
         return;

      if (file.isDirectory()) {
         for (File f : file.listFiles(new TemplateFileNameFilter())) {
            readTemplates(f);
         }
         return;
      }

      try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
         StringBuilder sb = new StringBuilder();
         byte[] buffer = new byte[4096];
         int len = 0;
         while ((len = fis.read(buffer)) > 0) {
            sb.append(new String(buffer, 0, len));
         }

         this.templateList.put(file.getName(), sb.toString());
      }
   }


   private void writeClass(ClassHolder clazz, String classTemplate) throws IOException {
      String templ = classTemplate
            .replaceAll("\\{PACKAGE_NAME\\}", clazz.getPackageName())
            .replaceAll("\\{VERSION\\}", clazz.getVersion())
            .replaceAll("\\{CLASS_NAME\\}", clazz.getName())
            .replaceAll("\\{BASE_CLASS\\}", clazz.getExtendsFrom())
            .replaceAll("\\{INTERFACE\\}", clazz.getImplementsFrom());

      BufferedReader br = new BufferedReader(new StringReader(templ));
      String line = null;
      StringBuilder sb = new StringBuilder();
      while ((line = br.readLine()) != null) {
         if (line.startsWith("{HAS_BASECLASS}")) {
            if (clazz.hasBaseClass())
               sb.append(line.substring("{HAS_BASECLASS}".length()) + this.nl);
         }
         else if (line.startsWith("{HAS_NO_BASECLASS}")) {
            if (!clazz.hasBaseClass())
               sb.append(line.substring("{HAS_NO_BASECLASS}".length()) + this.nl);
         }
         else
            sb.append(line + this.nl);
      }

      templ = runTemplate(clazz, sb.toString());
      templ = runTemplateForMethods(clazz, templ);

      for (String section : SECTIONS)
         templ = runTemplate(clazz, templ, section);

      File f = new File(
            this.outputPath + System.getProperty("file.separator") + clazz.getPackageName().replaceAll("\\.", "\\/"));
      f.mkdirs();
      try (FileOutputStream fos = new FileOutputStream(f.getAbsolutePath() + System.getProperty("file.separator") + clazz.getName() + ".java")) {
         fos.write(templ.getBytes());
         fos.flush();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }


   private String runTemplate(ClassHolder clazz, String template) {
      String ret = template;

      int startIndex = ret.indexOf("{CONSTANTS_START}");

      if (startIndex < 0)
         return ret;

      int endIndex = ret.indexOf("{CONSTANTS_END}");
      if (endIndex < 0)
         return ret;

      String before = ret.substring(0, startIndex);
      String inner = ret.substring(startIndex + "{CONSTANTS_START}".length(), endIndex);
      String after = ret.substring(endIndex + "{CONSTANTS_END}".length());

      StringBuilder sb = new StringBuilder(before);

      for (ConstantHolder v : clazz.getConstants())
         sb
               .append(
                     inner
                           .replaceAll("\\{COMMENT\\}", v.getComment())
                           .replaceAll("\\{FIELD_TYPE\\}", v.getType())
                           .replaceAll("\\{FIELD_NAME_WITH_VALUE\\}", v.getFieldWithValue()));

      sb.append(after);

      return sb.toString();
   }


   private String runTemplateForMethods(ClassHolder clazz, String template) {
      String ret = template;

      int startIndex = ret.indexOf("{SPECIAL_METHODS_START}");

      if (startIndex < 0)
         return ret;

      int endIndex = ret.indexOf("{SPECIAL_METHODS_END}");
      if (endIndex < 0)
         return ret;

      String before = ret.substring(0, startIndex);
      String inner = ret.substring(startIndex + "{SPECIAL_METHODS_START}".length(), endIndex);
      String after = ret.substring(endIndex + "{SPECIAL_METHODS_END}".length());

      StringBuilder sb = new StringBuilder(before);

      for (String v : clazz.getSpecialMethods())
         sb.append(inner.replaceAll("\\{METHOD\\}", v));

      sb.append(after);

      return sb.toString();
   }


   private String runTemplate(ClassHolder clazz, String template, String section) {
      String ret = template;

      int startIndex = ret.indexOf("{" + section + "_START}");

      if (startIndex < 0)
         return ret;

      int endIndex = ret.indexOf("{" + section + "_END}");
      if (endIndex < 0)
         return ret;

      String before = ret.substring(0, startIndex);
      String inner = ret.substring(startIndex + ("{" + section + "_START}").length(), endIndex);
      String after = ret.substring(endIndex + ("{" + section + "_END}").length());

      StringBuilder sb = new StringBuilder(before);
      
      for (VariableHolder v : clazz.getVariables())
         sb
               .append(
                     inner
                           .replaceAll("\\{COMMENT\\}", v.getComment())
                           .replaceAll("\\{FIELD_TYPE\\}", v.getType())
                           .replaceAll("\\{FIELD_NAME_WITH_VALUE\\}", v.getFieldWithValue())
                           .replaceAll("\\{FIELD_NAME\\}", v.getFieldName())
                           .replaceAll("\\{METHOD_NAME\\}", v.getMethodName())
                           .replaceAll("\\{PRINT_NAME\\}", v.getMethodName())
                           .replaceAll("\\{PRINT_VALUE\\}", v.getPrintValue())
                           .replaceAll("\\{METHOD\\}", v.writeSpecialMethods())
                           .replaceAll("\\{ANNOTATION\\}", v.getAnnotation()));

      sb.append(after);

      return sb.toString();
   }


   private List<ConstantHolder> getConstants(NodeList nodes) {
      List<ConstantHolder> list = new ArrayList<>();

      if (nodes == null)
         return list;

      for (int i = 0; i < nodes.getLength(); i++ ) {
         Node nNode = nodes.item(i);
         if (nNode.getNodeType() == Node.ELEMENT_NODE && "const".equals(nNode.getNodeName())) {
            Element eElement = (Element)nNode;

            ConstantHolder cl = new ConstantHolder();

            cl.setName(eElement.getAttribute("name"));
            cl.setType(eElement.getAttribute("type"));
            cl.setValue(eElement.getAttribute("value"));
            cl.setComment(eElement.getAttribute("comment"));

            list.add(cl);
         }
      }

      return list;
   }


   private List<VariableHolder> getVariables(NodeList nodes) {
      List<VariableHolder> list = new ArrayList<>();

      if (nodes == null)
         return list;

      for (int i = 0; i < nodes.getLength(); i++ ) {
         Node nNode = nodes.item(i);
         if (nNode.getNodeType() == Node.ELEMENT_NODE && "variable".equals(nNode.getNodeName())) {
            Element eElement = (Element)nNode;

            VariableHolder cl = new VariableHolder();

            cl.setName(eElement.getAttribute("name"));
            cl.setType(eElement.getAttribute("type"));
            cl.setValue(eElement.getAttribute("value"));
            cl.setComment(eElement.getAttribute("comment"));
            cl.setDateFormat(eElement.getAttribute("format"));
            cl.setAnnotation(eElement.getAttribute("annotation"));

            list.add(cl);
         }
      }

      return list;
   }


   private List<String> getSpecialMethods(NodeList nodes) {
      List<String> list = new ArrayList<>();

      if (nodes == null)
         return list;

      for (int i = 0; i < nodes.getLength(); i++ ) {
         Node nNode = nodes.item(i);
         if (nNode.getNodeType() == Node.ELEMENT_NODE && "method".equals(nNode.getNodeName())) {
            Element eElement = (Element)nNode;

            list.add(eElement.getAttribute("value"));
         }
      }

      return list;
   }


   public static void main(String[] args) {
      try {
         new Xml2Class(args).doWork();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
}
