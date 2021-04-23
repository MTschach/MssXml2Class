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
   

   public TestKlasse () {
      // nothing to do here
   }
   

   public Integer getSessionId () { return this.sessionId; }
   

   public Boolean getBla () { return this.bla; }
   

   public de.mss.foo.FooClass getFoo () { return this.foo; }
   

   public java.util.Map<String, de.mss.foo.FooClass> getBar () { return this.bar; }
   

   public String getLoginPassword () { return this.loginPassword; }
   


   public void setSessionId (Integer v) { this.sessionId = v; }
   

   public void setBla (Boolean v) { this.bla = v; }
   

   public void setFoo (de.mss.foo.FooClass v) { this.foo = v; }
   

   public void setBar (java.util.Map<String, de.mss.foo.FooClass> v) { this.bar = v; }
   

   public void setLoginPassword (String v) { this.loginPassword = v; }
   


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


      

      

   }








   public String writeBar() {
      StringBuilder sb = new StringBuilder("size {" + this.bar.size() + "} ");
      for(java.util.Map.Entry<String, de.mss.foo.FooClass> e : this.bar.entrySet()) {
         if (e.getValue() != null) sb.append("[" + e.getKey() + "] {" + e.getValue().toString() + "} ");
      }
      return sb.toString();
   }





}
