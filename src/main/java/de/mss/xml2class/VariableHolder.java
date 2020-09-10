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


   @Override
   public String getDeclarationName() {
      if (this.name == null) {
         return null;
      }

      return getCamelCaseName(false);
   }


   public String getMethodName() {
      return getCamelCaseName(true);
   }


   public String getFieldName() {
      return getCamelCaseName(false);
   }


   public void setDateFormat(String v) {
      if (Tools.isSet(v)) {
         this.dateFormat = v;
      }
   }


   public String getDateFormat() {
      return this.dateFormat;
   }


   public void setAnnotation(String v) {
      this.annotation = v;
   }


   public String getAnnotation() {
      return this.annotation;
   }


   public String getPrintValue() {
      return getPrintValue(this.type, "this." + getFieldName());
   }


   public void setRequired(boolean v) {
      this.required = v;
   }


   public boolean isRequired() {
      return this.required;
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


   public boolean isSimpleArray() {
      return this.type.endsWith("[]");
   }


   public boolean isMap() {
      return this.type.contains("Map<");
   }


   public boolean isList() {
      return this.type.contains("List<");
   }


   public boolean isVector() {
      return this.type.contains("Vector<");
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


   private boolean isPrimitiveType(String t) {
      //@formatter:off
      return "BigDecimal".equalsIgnoreCase(t) ||
             "BigInteger".equalsIgnoreCase(t) ||
             "Boolean".equalsIgnoreCase(t) ||
             "Double".equalsIgnoreCase(t) ||
             "Float".equalsIgnoreCase(t) ||
             "Integer".equalsIgnoreCase(t)
          ;
      //@formatter:on
   }


   private boolean isSimpleType(String t) {
      //@formatter:off
      return "boolean".equalsIgnoreCase(t) ||
             "byte".equalsIgnoreCase(t) ||
             "double".equalsIgnoreCase(t) ||
             "float".equalsIgnoreCase(t) ||
             "int".equalsIgnoreCase(t) ||
             "short".equalsIgnoreCase(t) ||
             "long".equalsIgnoreCase(t)
          ;
      //@formatter:on
   }


   public String writeRequiredCheck(Map<String, ClassHolder> classList) {
      if (!this.required) {
         return "";
      }

      if (isSimpleType(this.type)) {
         return "";
      }

      final StringBuilder sb = new StringBuilder();

      sb.append("if (this." + getFieldName() + " == null)" + this.nl);
      sb
            .append(
                  "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \""
                        + this.getFieldName()
                        + " must not be null\");"
                        + this.nl);

      //      if (!isPrimitiveType(this.type)
      //            && !"String".equalsIgnoreCase(this.type)
      //            && !this.type.endsWith("Date")
      //            && !isList()
      //            && !isMap()
      //            && !isSimpleArray()
      //            && !isVector()) {
      if (hasSubtypeRequiredFields(this.type, classList)) {
         sb.append("this." + this.getFieldName() + ".checkRequiredFields();" + this.nl);
      }


      return sb.toString();
   }


   private boolean hasSubtypeRequiredFields(String subtype, Map<String, ClassHolder> classList) {

      for (final Entry<String, ClassHolder> clazz : classList.entrySet()) {
         if (subtype.equals(clazz.getKey())) {
            return clazz.getValue().hasRequiredFields();
         }
      }

      return false;
   }
}
