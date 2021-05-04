package de.mss.test;

public class TestKlasse implements java.io.Serializable {
   



   /** ne Konstante */
   public static final String EINE_KONSTANTE = "irgendwas";
   


   /**  */
   
   private Integer sessionId = null;
   

   /**  */
   
   private Boolean bla = true;
   

   /** ne subklasse */
   
   private de.mss.foo.FooClass foo = null;
   

   /**  */
   
   private java.util.Map<String, de.mss.foo.FooClass> bar = null;
   

   /**  */
   
   private String loginPassword = null;
   

   /**  */
   
   private String description = null;
   

   /**  */
   
   private String countryCode = null;
   

   /**  */
   
   private String minStringValue = null;
   

   /**  */
   
   private String maxStringValue = null;
   

   public TestKlasse () {
      // nothing to do here
   }
   

   public Integer getSessionId () { return this.sessionId; }
   

   public Boolean getBla () { return this.bla; }
   

   public de.mss.foo.FooClass getFoo () { return this.foo; }
   

   public java.util.Map<String, de.mss.foo.FooClass> getBar () { return this.bar; }
   

   public String getLoginPassword () { return this.loginPassword; }
   

   public String getDescription () { return this.description; }
   

   public String getCountryCode () { return this.countryCode; }
   

   public String getMinStringValue () { return this.minStringValue; }
   

   public String getMaxStringValue () { return this.maxStringValue; }
   


   public void setSessionId (Integer v) { this.sessionId = v; }

   public void setBla (Boolean v) { this.bla = v; }

   public void setFoo (de.mss.foo.FooClass v) { this.foo = v; }

   public void setBar (java.util.Map<String, de.mss.foo.FooClass> v) { this.bar = v; }

   public void setLoginPassword (String v) { this.loginPassword = v; }

   public void setDescription (String v) { this.description = v; }

   public void setCountryCode (String v) { this.countryCode = v; }

   public void setMinStringValue (String v) { this.minStringValue = v; }

   public void setMaxStringValue (String v) { this.maxStringValue = v; }


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(getClass().getName() + "[ ");

      if (this.sessionId != null)
         sb.append("SessionId {" + this.sessionId.toString() + "} ");

      if (this.bla != null)
         sb.append("Bla {" + this.bla.toString() + "} ");

      if (this.foo != null)
         sb.append("Foo {" + this.foo.toString() + "} ");

      if (this.bar != null)
         sb.append("Bar {" + writeBar() + "} ");

      if (this.loginPassword != null)
         sb.append("LoginPassword {" + "****" + "} ");

      if (this.description != null)
         sb.append("Description {" + this.description + "} ");

      if (this.countryCode != null)
         sb.append("CountryCode {" + this.countryCode + "} ");

      if (this.minStringValue != null)
         sb.append("MinStringValue {" + this.minStringValue + "} ");

      if (this.maxStringValue != null)
         sb.append("MaxStringValue {" + this.maxStringValue + "} ");

      sb.append("] ");
      return sb.toString();
   }

   public void checkRequiredFields() throws de.mss.utils.exception.MssException {

      

      if (this.bla == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "bla must not be null");
      }


      if (this.foo == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "foo must not be null");
      }
      this.foo.checkRequiredFields();


      

      

      if (this.description == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "description must not be null");
      }
      if (this.description.length() < 2) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "description is too short");
      }
      if (this.description.length() > 50) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "description is too long");
      }


      if (this.countryCode == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "countryCode must not be null");
      }
      if (this.countryCode.length() != 2) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "countryCode  must have a length of 2");
      }


      if (this.minStringValue == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "minStringValue must not be null");
      }
      if (this.minStringValue.length() < 2) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "minStringValue is too short");
      }


      if (this.maxStringValue == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "maxStringValue must not be null");
      }
      if (this.maxStringValue.length() > 50) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "maxStringValue is too long");
      }


   }








   public String writeBar() {
      StringBuilder sb = new StringBuilder("size {" + this.bar.size() + "} ");
      for(java.util.Map.Entry<String, de.mss.foo.FooClass> e : this.bar.entrySet()) {
         if (e.getValue() != null) sb.append("[" + e.getKey() + "] {" + e.getValue().toString() + "} ");
      }
      return sb.toString();
   }













}
