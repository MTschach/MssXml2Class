package de.mss.xml2class;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

   private static final String   CMD_TEMPLATES = "templates";
   private static final String   CMD_INPUT     = "input";
   private static final String   CMD_OUTPUT    = "output";

   private static final String[] SECTIONS      = {"VARIABLES", "GETTER", "SETTER", "TO_STRING", "PRINT_METHODS"};

   public static void main(String[] args) {
      try {
         new Xml2Class(args).doWork();
      }
      catch (final Exception e) {
         e.printStackTrace();
      }
   }

   private final String nl           = System.getProperty("line.separator");
   private String       templatePath = "templates";

   private String       inputPath    = "params";
   private String       outputPath   = "gen";


   private final Map<String, ClassHolder> classList    = new HashMap<>();

   private final Map<String, String>      templateList = new HashMap<>();


   public Xml2Class(String[] args) throws ParseException {
      init(args);
   }


   public void doWork() throws ParserConfigurationException, SAXException, IOException {
      System.out.println("Running MssXml2Class version 1.3.1");
      readXmlFiles(new File(this.inputPath));

      readTemplates(this.templatePath);

      for (final Entry<String, ClassHolder> entry : this.classList.entrySet()) {
         writeClass(entry.getValue());
      }
   }


   private List<ConstantHolder> getConstants(NodeList nodes) {
      final List<ConstantHolder> list = new ArrayList<>();

      if (nodes == null) {
         return list;
      }

      for (int i = 0; i < nodes.getLength(); i++ ) {
         final Node nNode = nodes.item(i);
         if (nNode.getNodeType() == Node.ELEMENT_NODE && "const".equals(nNode.getNodeName())) {
            final Element eElement = (Element)nNode;

            final ConstantHolder cl = new ConstantHolder();

            cl.setName(eElement.getAttribute("name"));
            cl.setType(eElement.getAttribute("type"));
            cl.setValue(eElement.getAttribute("value"));
            cl.setComment(eElement.getAttribute("comment"));

            list.add(cl);
         }
      }

      return list;
   }


   private List<String> getSpecialMethods(NodeList nodes) {
      final List<String> list = new ArrayList<>();

      if (nodes == null) {
         return list;
      }

      for (int i = 0; i < nodes.getLength(); i++ ) {
         final Node nNode = nodes.item(i);
         if (nNode.getNodeType() == Node.ELEMENT_NODE && "method".equals(nNode.getNodeName())) {
            final Element eElement = (Element)nNode;

            list.add(eElement.getAttribute("value"));
         }
      }

      return list;
   }


   private List<VariableHolder> getVariables(NodeList nodes) {
      final List<VariableHolder> list = new ArrayList<>();

      if (nodes == null) {
         return list;
      }

      for (int i = 0; i < nodes.getLength(); i++ ) {
         final Node nNode = nodes.item(i);
         if (nNode.getNodeType() == Node.ELEMENT_NODE && "variable".equals(nNode.getNodeName())) {
            final Element eElement = (Element)nNode;

            final VariableHolder cl = new VariableHolder();

            cl.setName(eElement.getAttribute("name"));
            cl.setType(eElement.getAttribute("type"));
            cl.setValue(eElement.getAttribute("value"));
            cl.setComment(eElement.getAttribute("comment"));
            cl.setDateFormat(eElement.getAttribute("format"));
            cl.setAnnotation(eElement.getAttribute("annotation"));
            cl.setRequired("true".equalsIgnoreCase(eElement.getAttribute("required")));
            cl.setMaxLength(eElement.getAttribute("maxLength"));
            cl.setMinLength(eElement.getAttribute("minLength"));
            cl.setThrowsException(eElement.getAttribute("throws"));
            cl.setOverrides("true".equalsIgnoreCase(eElement.getAttribute("overrides")));

            list.add(cl);
         }
      }

      return list;
   }


   private void init(String[] args) throws ParseException {
      final Options cmdArgs = new Options();

      final Option t = new Option("t", CMD_TEMPLATES, true, "template path");
      t.setRequired(false);
      cmdArgs.addOption(t);

      final Option o = new Option("o", CMD_OUTPUT, true, "output path");
      o.setRequired(false);
      cmdArgs.addOption(o);

      final Option i = new Option("i", CMD_INPUT, true, "input path");
      i.setRequired(false);
      cmdArgs.addOption(i);

      final CommandLineParser parser = new DefaultParser();
      final CommandLine cmd = parser.parse(cmdArgs, args);

      if (cmd.hasOption(CMD_TEMPLATES)) {
         this.templatePath = cmd.getOptionValue(CMD_TEMPLATES);
      }

      if (cmd.hasOption(CMD_OUTPUT)) {
         this.outputPath = cmd.getOptionValue(CMD_OUTPUT);
      }

      if (cmd.hasOption(CMD_INPUT)) {
         this.inputPath = cmd.getOptionValue(CMD_INPUT);
      }
   }


   private void readTemplateFromZip(ZipInputStream zis, ZipEntry ze) throws IOException {
      final StringBuilder sb = new StringBuilder();
      final byte[] buffer = new byte[4096];
      int len = 0;
      while ((len = zis.read(buffer)) > 0) {
         sb.append(new String(buffer, 0, len));
      }

      final String name = ze.getName().substring(ze.getName().lastIndexOf("/"));
      this.templateList.put(name, sb.toString());
   }


   private void readTemplates(File file, int maxDepth) throws IOException {
      if (maxDepth <= 0) {
         return;
      }

      if (file.isDirectory()) {
         for (final File f : file.listFiles(new TemplateFileNameFilter())) {
            readTemplates(f, maxDepth - 1);
         }
         return;
      }

      try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
         final StringBuilder sb = new StringBuilder();
         final byte[] buffer = new byte[4096];
         int len = 0;
         while ((len = fis.read(buffer)) > 0) {
            sb.append(new String(buffer, 0, len));
         }

         this.templateList.put(file.getName(), sb.toString());
      }
   }


   private void readTemplates(String tp) throws IOException {
      if (tp == null) {
         return;
      }

      final File dir = new File(tp);
      if (!dir.exists()) {
         readTemplatesFromJar(tp);
         return;
      }

      readTemplates(dir, 1);
   }


   private void readTemplatesFromJar(String tp) throws IOException {
      final CodeSource src = Xml2Class.class.getProtectionDomain().getCodeSource();
      if (src == null) {
         return;
      }

      final URL jar = src.getLocation();
      if (jar == null) {
         return;
      }

      try (ZipInputStream zis = new ZipInputStream(jar.openStream())) {
         ZipEntry ze;
         while ((ze = zis.getNextEntry()) != null) {
            if (ze.isDirectory() || !ze.getName().startsWith(tp) || !ze.getName().endsWith("template")) {
               continue;
            }
            readTemplateFromZip(zis, ze);
         }
      }
   }


   public void readXmlFiles(File file) throws ParserConfigurationException, SAXException, IOException {
      if (!file.exists()) {
         return;
      }

      if (file.isDirectory()) {
         for (final File f : file.listFiles(new XmlFileNameFilter())) {
            readXmlFiles(f);
         }
         return;
      }

      final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      final Document doc = dBuilder.parse(file);

      doc.getDocumentElement().normalize();

      final NodeList nList = doc.getElementsByTagName("classes").item(0).getChildNodes();

      for (int temp = 0; temp < nList.getLength(); temp++ ) {
         final Node nNode = nList.item(temp);

         if (nNode.getNodeType() == Node.ELEMENT_NODE) {

            final Element eElement = (Element)nNode;

            final ClassHolder cl = new ClassHolder();

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


   private String runTemplate(ClassHolder clazz, String template) {
      final String ret = template;

      final int startIndex = ret.indexOf("{CONSTANTS_START}");

      if (startIndex < 0) {
         return ret;
      }

      final int endIndex = ret.indexOf("{CONSTANTS_END}");
      if (endIndex < 0) {
         return ret;
      }

      final String before = ret.substring(0, startIndex);
      final String inner = ret.substring(startIndex + "{CONSTANTS_START}".length(), endIndex);
      final String after = ret.substring(endIndex + "{CONSTANTS_END}".length());

      final StringBuilder sb = new StringBuilder(before);

      for (final ConstantHolder v : clazz.getConstants()) {
         sb
               .append(
                     inner
                           .replaceAll("\\{COMMENT\\}", v.getComment())
                           .replaceAll("\\{FIELD_TYPE\\}", v.getType())
                           .replaceAll("\\{FIELD_NAME_WITH_VALUE\\}", v.getFieldWithValue()));
      }

      sb.append(after);

      return sb.toString();
   }


   private String runTemplate(ClassHolder clazz, String template, String section) throws IOException {
      final String ret = template;

      final int startIndex = ret.indexOf("{" + section + "_START}");

      if (startIndex < 0) {
         return ret;
      }

      final int endIndex = ret.indexOf("{" + section + "_END}");
      if (endIndex < 0) {
         return ret;
      }

      final String before = ret.substring(0, startIndex);
      final String inner = ret.substring(startIndex + ("{" + section + "_START}").length(), endIndex);
      final String after = ret.substring(endIndex + ("{" + section + "_END}").length());

      final StringBuilder sb = new StringBuilder(before);

      for (final VariableHolder v : clazz.getVariables()) {
         final String str = inner
               .replaceAll("\\{COMMENT\\}", v.getComment())
               .replaceAll("\\{FIELD_TYPE\\}", v.getType())
               .replaceAll("\\{FIELD_NAME_WITH_VALUE\\}", v.getFieldWithValue())
               .replaceAll("\\{FIELD_NAME\\}", v.getFieldName())
               .replaceAll("\\{METHOD_NAME\\}", v.getMethodName())
               .replaceAll("\\{PRINT_NAME\\}", v.getMethodName())
               .replaceAll("\\{PRINT_VALUE\\}", v.getPrintValue())
               .replaceAll("\\{METHOD\\}", v.writeSpecialMethods())
               .replaceAll("\\{ANNOTATION\\}", v.getAnnotation())
               .replaceAll("\\{CHECK\\}", v.writeRequiredCheck(this.classList))
               .replaceAll("\\{THROWS\\}", v.writeThrowsException())
               .replaceAll("\\{OVERRIDES\\}", v.writeOverrides());

         final BufferedReader br = new BufferedReader(new StringReader(str));
         String line = null;
         while ((line = br.readLine()) != null) {
            if (line.startsWith("{IS_ENUM}")) {
               if (v.isEnum()) {
                  sb.append(line.substring("{IS_ENUM}".length()));
               }
            } else {
               sb.append(line + this.nl);
            }
         }
      }

      sb.append(after);

      return sb.toString();
   }


   private String runTemplateForMethods(ClassHolder clazz, String template) {
      final String ret = template;

      final int startIndex = ret.indexOf("{SPECIAL_METHODS_START}");

      if (startIndex < 0) {
         return ret;
      }

      final int endIndex = ret.indexOf("{SPECIAL_METHODS_END}");
      if (endIndex < 0) {
         return ret;
      }

      final String before = ret.substring(0, startIndex);
      final String inner = ret.substring(startIndex + "{SPECIAL_METHODS_START}".length(), endIndex);
      final String after = ret.substring(endIndex + "{SPECIAL_METHODS_END}".length());

      final StringBuilder sb = new StringBuilder(before);

      for (final String v : clazz.getSpecialMethods()) {
         sb.append(inner.replaceAll("\\{METHOD\\}", v));
      }

      sb.append(after);

      return sb.toString();
   }


   private String runTemplateForRequired(ClassHolder clazz, String template) throws IOException {
      final String ret = template;

      final int startIndex = ret.indexOf("{REQUIRED_START}");

      if (startIndex < 0) {
         return ret;
      }

      final int endIndex = ret.indexOf("{REQUIRED_END}");
      if (endIndex < 0) {
         return ret;
      }

      final String before = ret.substring(0, startIndex);
      final String inner = ret.substring(startIndex + "{REQUIRED_START}".length(), endIndex);
      final String after = ret.substring(endIndex + "{REQUIRED_END}".length());

      final StringBuilder sb = new StringBuilder(before);

      if (clazz.hasRequiredFields()) {
         sb.append(runTemplate(clazz, inner, "CHECK_REQUIRED"));
      }

      sb.append(after);

      return sb.toString();
   }


   private void writeClass(ClassHolder clazz) throws IOException {
      for (final Entry<String, String> entry : this.templateList.entrySet()) {
         writeClass(clazz, entry.getValue(), entry.getKey());
      }

   }


   private void writeClass(ClassHolder clazz, String classTemplate, String templateName) throws IOException {

      Pattern pattern = Pattern.compile("\\{CLASS_SUFFIX_(.*?)\\}");
      Matcher matcher = pattern.matcher(classTemplate);
      String classSuffix = "";
      String classPrefix = "";
      if (matcher.find()) {
         classSuffix = matcher.group(1);
      }
      pattern = Pattern.compile("\\{CLASS_PREFIX_(.*?)\\}");
      matcher = pattern.matcher(classTemplate);
      if (matcher.find()) {
         classPrefix = matcher.group(1);
      }

      System.out.println("Generating Class " + classPrefix + clazz.getName() + classSuffix + " with template " + templateName);

      String templ = classTemplate
            .replaceAll("\\{CLASS_PREFIX_(.*?)\\}", "")
            .replaceAll("\\{CLASS_SUFFIX_(.*?)\\}", "")
            .replaceAll("\\{PACKAGE_NAME\\}", clazz.getPackageName())
            .replaceAll("\\{VERSION\\}", clazz.getVersion())
            .replaceAll("\\{CLASS_NAME\\}", clazz.getName())
            .replaceAll("\\{BASE_CLASS\\}", clazz.getExtendsFrom())
            .replaceAll("\\{INTERFACE\\}", clazz.getImplementsFrom());

      final BufferedReader br = new BufferedReader(new StringReader(templ));
      String line = null;
      final StringBuilder sb = new StringBuilder();
      while ((line = br.readLine()) != null) {
         if (line.startsWith("{HAS_BASECLASS}")) {
            if (clazz.hasBaseClass()) {
               sb.append(line.substring("{HAS_BASECLASS}".length()) + this.nl);
            }
         } else if (line.startsWith("{HAS_NO_BASECLASS}")) {
            if (!clazz.hasBaseClass()) {
               sb.append(line.substring("{HAS_NO_BASECLASS}".length()) + this.nl);
            }
         } else {
            sb.append(line + this.nl);
         }
      }

      templ = runTemplate(clazz, sb.toString());
      templ = runTemplateForMethods(clazz, templ);
      templ = runTemplateForRequired(clazz, templ);

      for (final String section : SECTIONS) {
         templ = runTemplate(clazz, templ, section);
      }

      final File f = new File(
            this.outputPath + System.getProperty("file.separator") + clazz.getPackageName().replaceAll("\\.", "\\/"));
      f.mkdirs();
      try (
           FileOutputStream fos = new FileOutputStream(
                 f.getAbsolutePath() + System.getProperty("file.separator") + classPrefix + clazz.getName() + classSuffix + ".java")
      ) {
         fos.write(templ.getBytes());
         fos.flush();
      }
      catch (final IOException e) {
         e.printStackTrace();
      }
   }
}
