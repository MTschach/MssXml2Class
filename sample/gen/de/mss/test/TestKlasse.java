package de.mss.test;

public class TestKlasse
   extends de.mss.net.webservice.CheckRequiredFields
    implements java.io.Serializable, de.mss.net.webservice.IfCheckRequiredFields, de.mss.utils.logging.Logable {
   

   /** ne Konstante */
   public static final String EINE_KONSTANTE = "irgendwas";
   


   /**  */
   
   private Integer sessionId = null;

   /**  */
   
   private Boolean bla = true;

   /** ne subklasse */
   
   private de.mss.foo.FooClass foo = null;

   /**  */
   
   private java.util.Map<String, de.mss.foo.FooClass> barMap = null;

   /**  */
   
   private de.mss.foo.BarClass bar = null;

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

   
   public java.util.Map<String, de.mss.foo.FooClass> getBarMap () { return this.barMap; }

   
   public de.mss.foo.BarClass getBar () { return this.bar; }

   
   public String getLoginPassword () { return this.loginPassword; }

   
   public String getDescription () { return this.description; }

   
   public String getCountryCode () { return this.countryCode; }

   
   public String getMinStringValue () { return this.minStringValue; }

   
   public String getMaxStringValue () { return this.maxStringValue; }


   
   public void setSessionId (Integer v)  { this.sessionId = v; }

   
   public void setBla (Boolean v)  { this.bla = v; }

   
   public void setFoo (de.mss.foo.FooClass v)  { this.foo = v; }

   
   public void setBarMap (java.util.Map<String, de.mss.foo.FooClass> v)  { this.barMap = v; }

   
   public void setBar (de.mss.foo.BarClass v)  { this.bar = v; }

   
   public void setLoginPassword (String v)  { this.loginPassword = v; }

   
   public void setDescription (String v)  { this.description = v; }

   
   public void setCountryCode (String v)  { this.countryCode = v; }

   
   public void setMinStringValue (String v)  { this.minStringValue = v; }

   
   public void setMaxStringValue (String v)  { this.maxStringValue = v; }


   @Override
   public java.util.Map<String,String> doLogging() {
      java.util.Map<String, String> ret = new java.util.HashMap<>();
      ret = de.mss.utils.logging.LoggingUtil.addLogging("SessionId", this.sessionId, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("Bla", this.bla, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("Foo", this.foo, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("BarMap", this.barMap, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("Bar", this.bar, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("LoginPassword", this.loginPassword, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("Description", this.description, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("CountryCode", this.countryCode, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("MinStringValue", this.minStringValue, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("MaxStringValue", this.maxStringValue, ret);

      return ret;
   }

   @Override
   public String toString() {
      return de.mss.utils.logging.LoggingUtil.getLogString(doLogging());
   }
   
   
   @Override
   public void checkRequiredFields() throws de.mss.utils.exception.MssException {

      if (this.bla == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "bla must not be null");
      }

      if (this.foo == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "foo must not be null");
      }
      this.foo.checkRequiredFields();

      if (this.bar == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "bar must not be null");
      }
      this.bar.checkRequiredFields();

      if (this.description == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "description must not be null");
      }
      checkMinStingLength(this.description, 2, "description");
      checkMaxStingLength(this.description, 50, "description");

      if (this.countryCode == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "countryCode must not be null");
      }
      checkStingLength(this.countryCode, 2, "countryCode");

      if (this.minStringValue == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "minStringValue must not be null");
      }
      checkMinStingLength(this.minStringValue, 2, "minStringValue");

      if (this.maxStringValue == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "maxStringValue must not be null");
      }
      checkMaxStingLength(this.maxStringValue, 50, "maxStringValue");

   }

   private String writeBarMap() {
      StringBuilder sb = new StringBuilder("size {" + this.barMap.size() + "} ");
      for(java.util.Map.Entry<String, de.mss.foo.FooClass> e : this.barMap.entrySet()) {
         if (e.getValue() != null) sb.append("[" + e.getKey() + "] {" + e.getValue().toString() + "} ");
      }
      return sb.toString();
   }


}
