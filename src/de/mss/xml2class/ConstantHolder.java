package de.mss.xml2class;

import de.mss.utils.Tools;

public class ConstantHolder {

   protected String nl      = System.getProperty("line.separator");

   protected String name    = null;
   protected String type    = null;
   protected String value   = null;
   protected String comment = null;

   public ConstantHolder() {
      // nothing to do here
   }


   public String getComment() {
      return this.comment;
   }


   public String getDeclarationName() {
      return this.name.toUpperCase();
   }


   public String getFieldWithValue() {
      final StringBuilder sb = new StringBuilder();

      sb.append(getDeclarationName());
      if (Tools.isSet(this.value)) {
         sb.append(" = " + writeValue());
      }

      return sb.toString();
   }


   public String getName() {
      return this.name;
   }


   public String getType() {
      return this.type;
   }


   public String getValue() {
      return this.value;
   }


   public void setComment(String n) {
      this.comment = n;
   }


   public void setName(String n) {
      this.name = n;
   }


   public void setType(String n) {
      this.type = n;
   }


   public void setValue(String n) {
      this.value = n;
   }


   @Override
   public String toString() {
      return "Name: " + this.name + ", Type: " + this.type;
   }


   protected String writeValue() {
      if (this.value == null || this.value.length() == 0 || "null".equalsIgnoreCase(this.value)) {
         return "null";
      } else if ("String".equals(this.type)) {
         return "\"" + this.value + "\"";
      } else {
         return this.value;
      }
   }
}
