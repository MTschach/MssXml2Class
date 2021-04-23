package de.mss.xml2class;

import java.util.Map;
import java.util.Map.Entry;

import de.mss.utils.Tools;

public class VariableHolder extends ConstantHolder {

   private String  lowerName  = null;
   private String  upperName  = null;
   private String  dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
   private String  annotation = null;
   private boolean required   = false;

   public VariableHolder() {
      // nothing to do here
   }


   public String getAnnotation() {
      return this.annotation;
   }


   private String getCamelCaseName(boolean toUpper) {
      if (this.name == null) {
         return null;
      }

      final byte[] b = this.name.getBytes();

      if (toUpper) {
         if (this.upperName != null) {
            return this.upperName;
         }

         if (Character.isUpperCase(b[0])) {
            return this.name;
         }

         b[0] = (byte)Character.toUpperCase(b[0]);

         this.upperName = new String(b);
         return this.upperName;
      }

      if (this.lowerName != null) {
         return this.lowerName;
      }

      if (Character.isLowerCase(b[0])) {
         return this.name;
      }

      b[0] = (byte)Character.toLowerCase(b[0]);

      this.lowerName = new String(b);
      return this.lowerName;
   }


   public String getDateFormat() {
      return this.dateFormat;
   }


   @Override
   public String getDeclarationName() {
      if (this.name == null) {
         return null;
      }

      return getCamelCaseName(false);
   }


   public String getFieldName() {
      return getCamelCaseName(false);
   }


   public String getMethodName() {
      return getCamelCaseName(true);
   }


   public String getPrintValue() {
      return getPrintValue(this.type, "this." + getFieldName());
   }


   private String getPrintValue(String t, String v) {
      if (isSimpleType(t)) {
         return v;
      } else if (isPrimitiveType(t)) {
         return v + ".toString()";
      } else if ("String".equals(t)) {
         return !this.name.toUpperCase().contains("PASSWORD") ? v : "\"****\"";
      } else if (t.endsWith("Date")) {
         return "new java.text.SimpleDateFormat(\"" + this.dateFormat + "\").format(" + v + ")";
      } else if (t.endsWith("[]")) {
         return "write" + getMethodName() + "()";
      } else if (t.contains("Map<")) {
         return "write" + getMethodName() + "()";
      } else if (t.contains("List<")) {
         return "write" + getMethodName() + "()";
      } else if (t.contains("Vector<")) {
         return "write" + getMethodName() + "()";
      } else {
         return v + ".toString()";
      }
   }


   private boolean hasSubtypeRequiredFields(String subtype, Map<String, ClassHolder> classList) {

      for (final Entry<String, ClassHolder> clazz : classList.entrySet()) {
         if (subtype.equals(clazz.getKey())) {
            return clazz.getValue().hasRequiredFields();
         }
      }

      return false;
   }


   public boolean isList() {
      return this.type.contains("List<");
   }


   public boolean isMap() {
      return this.type.contains("Map<");
   }


   private boolean isPrimitiveType(String t) {
      //@formatter:off
      return "java.math.BigDecimal".equals(t) ||
             "java.math.BigInteger".equals(t) ||
             "Boolean".equals(t) ||
             "Double".equals(t) ||
             "Float".equals(t) ||
             "Integer".equals(t)
          ;
      //@formatter:on
   }


   public boolean isRequired() {
      return this.required;
   }


   public boolean isSimpleArray() {
      return this.type.endsWith("[]");
   }


   private boolean isSimpleType(String t) {
      //@formatter:off
      return "boolean".equals(t) ||
             "byte".equalsIgnoreCase(t) ||
             "double".equals(t) ||
             "float".equals(t) ||
             "int".equalsIgnoreCase(t) ||
             "short".equalsIgnoreCase(t) ||
             "long".equalsIgnoreCase(t)
          ;
      //@formatter:on
   }


   public boolean isVector() {
      return this.type.contains("Vector<");
   }


   public void setAnnotation(String v) {
      this.annotation = v;
   }


   public void setDateFormat(String v) {
      if (Tools.isSet(v)) {
         this.dateFormat = v;
      }
   }


   public void setRequired(boolean v) {
      this.required = v;
   }


   @Override
   public String toString() {
      return super.toString() + ", required: " + this.required;
   }


   private String writeListValue() {
      final StringBuilder sb = new StringBuilder();

      final String subType = this.type.substring(this.type.indexOf("List<") + 5, this.type.length() - 1);

      sb.append("   public String write" + getMethodName() + "() {" + this.nl);
      sb.append("      StringBuilder sb = new StringBuilder(\"size {\" + this." + getFieldName() + ".size() + \"} \");" + this.nl);

      sb.append("      for(int i=0; i<this." + getFieldName() + ".size(); i++) {" + this.nl);
      if (isSimpleType(subType)) {
         sb
               .append(
                     "         sb.append(\"[\" + i + \"] {\" + "
                           + getPrintValue(subType, "this." + getFieldName() + ".get(i)")
                           + " + \"} \");"
                           + this.nl);
      } else {
         sb
               .append(
                     "         if (this."
                           + getFieldName()
                           + ".get(i) != null) sb.append(\"[\" + i + \"] {\" + "
                           + getPrintValue(subType, "this." + getFieldName() + ".get(i)")
                           + " + \"} \");"
                           + this.nl);
      }
      sb.append("      }" + this.nl);

      sb.append("      return sb.toString();" + this.nl);
      sb.append("   }" + this.nl + this.nl + this.nl);

      return sb.toString();
   }


   private String writeMapValue() {
      final StringBuilder sb = new StringBuilder();

      final String[] subType = this.type.substring(this.type.indexOf("Map<") + 4, this.type.length() - 1).split(",");

      sb.append("   public String write" + getMethodName() + "() {" + this.nl);
      sb.append("      StringBuilder sb = new StringBuilder(\"size {\" + this." + getFieldName() + ".size() + \"} \");" + this.nl);

      sb.append("      for(java.util.Map.Entry<" + subType[0] + "," + subType[1] + "> e : this." + getFieldName() + ".entrySet()) {" + this.nl);
      if (isSimpleType(subType[1])) {
         sb
               .append(
                     "         sb.append(\"[\" + e.getKey() + \"] {\" + "
                           + getPrintValue(subType[1], "e.getValue()")
                           + " + \"} \");"
                           + this.nl);
      } else {
         sb
               .append(
                     "         if (e.getValue() != null) sb.append(\"[\" + e.getKey() + \"] {\" + "
                           + getPrintValue(subType[1], "e.getValue()")
                           + " + \"} \");"
                           + this.nl);
      }
      sb.append("      }" + this.nl);

      sb.append("      return sb.toString();" + this.nl);
      sb.append("   }" + this.nl);

      return sb.toString();
   }


   public String writeRequiredCheck(Map<String, ClassHolder> classList) {
      if (!this.required) {
         return "";
      }

      if (isSimpleType(this.type)) {
         return "";
      }

      final StringBuilder sb = new StringBuilder();

      sb.append("if (this." + getFieldName() + " == null) {" + this.nl);
      sb.append(writeThrow("must not be null"));
      sb.append("      }" + this.nl);

      if (isList() || isMap() || isVector()) {
         sb.append("      if (this." + getFieldName() + ".isEmpty()) {" + this.nl);
         sb.append(writeThrow("must not be empty"));
         sb.append("      }" + this.nl);
      }

      if (isSimpleArray()) {
         sb.append("      if (this." + this.getFieldName() + ".length == 0) {" + this.nl);
         sb.append(writeThrow("must not be empty"));
         sb.append("      }" + this.nl);
      }

      if (hasSubtypeRequiredFields(this.type, classList)) {
         sb.append("      this." + this.getFieldName() + ".checkRequiredFields();" + this.nl);
      }


      return sb.toString();
   }


   private String writeSimpleArrayValue() {
      final StringBuilder sb = new StringBuilder();

      final String subType = this.type.substring(0, this.type.length() - 2);

      sb.append("   public String write" + getMethodName() + "() {" + this.nl);
      sb.append("      StringBuilder sb = new StringBuilder(\"size {\" + this." + getFieldName() + ".length + \"} \");" + this.nl);

      sb.append("      for(int i=0; i<this." + getFieldName() + ".length; i++) {" + this.nl);
      if (isSimpleType(subType)) {
         sb.append("         sb.append(\"[\" + i + \"] {\" + " + getPrintValue(subType, "this." + getFieldName() + "[i]") + " + \"} \");" + this.nl);
      } else {
         sb
               .append(
                     "         if (this."
                           + getFieldName()
                           + "[i] != null) sb.append(\"[\" + i + \"] {\" + "
                           + getPrintValue(subType, "this." + getFieldName() + "[i]")
                           + " + \"} \");"
                           + this.nl);
      }
      sb.append("      }" + this.nl);

      sb.append("      return sb.toString();" + this.nl);
      sb.append("   }" + this.nl + this.nl + this.nl);

      return sb.toString();
   }


   public String writeSpecialMethods() {

      if (isSimpleArray()) {
         return writeSimpleArrayValue();
      } else if (isList()) {
         return writeListValue();
      } else if (isMap()) {
         return writeMapValue();
      } else if (isVector()) {
         return writeVectorValue();
      }

      return "";
   }


   private String writeThrow(String msg) {
      return "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \""
            + this.getFieldName()
            + " "
            + msg
            + "\");"
            + this.nl;
   }


   private String writeVectorValue() {
      final StringBuilder sb = new StringBuilder();

      final String subType = this.type.substring(this.type.indexOf("Vector<") + 7, this.type.length() - 1);

      sb.append("   public String write" + getMethodName() + "() {" + this.nl);
      sb.append("      StringBuilder sb = new StringBuilder(\"size {\" + this." + getFieldName() + ".size() + \"} \");" + this.nl);

      sb.append("      for(int i=0; i<this." + getFieldName() + ".size(); i++) {" + this.nl);
      if (isSimpleType(subType)) {
         sb
               .append(
                     "         sb.append(\"[\" + i + \"] {\" + "
                           + getPrintValue(subType, "this." + getFieldName() + ".get(i)")
                           + " + \"} \");"
                           + this.nl);
      } else {
         sb
               .append(
                     "         if (this."
                           + getFieldName()
                           + ".get(i) != null) sb.append(\"[\" + i + \"] {\" + "
                           + getPrintValue(subType, "this." + getFieldName() + ".get(i)")
                           + " + \"} \");"
                           + this.nl);
      }
      sb.append("      }" + this.nl);

      sb.append("      return sb.toString();" + this.nl);
      sb.append("   }" + this.nl + this.nl + this.nl);

      return sb.toString();
   }
}
