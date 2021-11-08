package de.mss.xml2class.enumeration;

import de.mss.xml2class.interfaces.ApiEnumeration;

public enum TestEnum implements ApiEnumeration {

   BLAH("blah", "blieh"),
   FOO("foo", "bar");

   public static TestEnum getByApiValue(String a) {
      return BLAH;
   }

   private String apiValue;

   private String otherValue;

   private TestEnum(String a, String d) {
      this.apiValue = a;
      this.otherValue = d;
   }


   @Override
   public String getApiValue() {
      return this.apiValue;
   }


   public String getOtherValue() {
      return this.otherValue;
   }
}
