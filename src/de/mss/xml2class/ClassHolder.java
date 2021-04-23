package de.mss.xml2class;

import java.util.ArrayList;
import java.util.List;

import de.mss.utils.Tools;

public class ClassHolder {

   private String               name           = null;
   private String               packageName    = null;
   private String               extendsFrom    = null;
   private String               implementsFrom = null;
   private String               version        = null;
   private String               comment        = null;

   private List<ConstantHolder> constants      = new ArrayList<>();
   private List<VariableHolder> variables      = new ArrayList<>();
   private List<String>         specialMethods = new ArrayList<>();


   public ClassHolder() {
      // nothing to do here
   }


   public void addConstant(ConstantHolder c) {
      if (c == null) {
         return;
      }

      if (this.constants == null) {
         this.constants = new ArrayList<>();
      }

      this.constants.add(c);
   }


   public void addVariable(VariableHolder c) {
      if (c == null) {
         return;
      }

      if (this.variables == null) {
         this.variables = new ArrayList<>();
      }

      this.variables.add(c);
   }


   public String getComment() {
      return this.comment;
   }


   public List<ConstantHolder> getConstants() {
      return this.constants;
   }


   public String getExtendsFrom() {
      if (Tools.isSet(this.extendsFrom)) {
         return " extends " + this.extendsFrom;
      }

      return "";
   }


   public String getImplementsFrom() {
      if (Tools.isSet(this.implementsFrom)) {
         return " implements " + this.implementsFrom;
      }

      return "";
   }


   public String getName() {
      return this.name;
   }


   public String getPackageName() {
      return this.packageName;
   }


   public List<String> getSpecialMethods() {
      return this.specialMethods;
   }


   public List<VariableHolder> getVariables() {
      return this.variables;
   }


   public String getVersion() {
      if (Tools.isSet(this.version)) {
         return "private static final long serialVersionUID = " + this.version + "l;";
      }

      return "";
   }


   public boolean hasBaseClass() {
      return Tools.isSet(this.extendsFrom);
   }


   public boolean hasInterface() {
      return Tools.isSet(this.implementsFrom);
   }


   public boolean hasRequiredFields() {

      for (final VariableHolder v : this.variables) {
         if (v.isRequired()) {
            return true;
         }
      }

      return false;
   }


   public void setComment(String n) {
      this.comment = n;
   }


   public void setConstants(List<ConstantHolder> l) {
      this.constants = l;
   }


   public void setExtendsFrom(String n) {
      this.extendsFrom = n;
   }


   public void setImplementsFrom(String n) {
      this.implementsFrom = n;
   }


   public void setName(String n) {
      this.name = n;
   }


   public void setPackageName(String n) {
      this.packageName = n;
   }


   public void setSpecialMethods(List<String> l) {
      this.specialMethods = l;
   }


   public void setVariables(List<VariableHolder> l) {
      this.variables = l;
   }


   public void setVersion(String n) {
      this.version = n;
   }


   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder("Name: " + this.name);
      if (this.packageName != null) {
         sb.append(", Package: " + this.packageName);
      }
      if (this.extendsFrom != null) {
         sb.append(", Extends: " + this.extendsFrom);
      }
      if (this.implementsFrom != null) {
         sb.append(", Interfaces: " + this.implementsFrom);
      }
      return sb.toString();
   }
}
