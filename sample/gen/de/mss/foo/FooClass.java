package de.mss.foo;

public class FooClass
    extends java.math.BigDecimal
   , de.mss.net.webservice.IfCheckRequiredFields, de.mss.utils.logging.Logable {
   


   /**  */
   
   private java.util.Date masterfoo;

   /**  */
   
   private byte[] buffer;

   /**  */
   
   private de.mss.xml2class.enumeration.TestEnum type;

   public FooClass () {
      super();
   }
   

   
   public java.util.Date getMasterfoo () { return this.masterfoo; }

   
   public byte[] getBuffer () { return this.buffer; }

   
   public de.mss.xml2class.enumeration.TestEnum getType () { return this.type; }


   
   public void setMasterfoo (java.util.Date v)  { this.masterfoo = v; }

   
   public void setBuffer (byte[] v)  { this.buffer = v; }

   
   public void setType (de.mss.xml2class.enumeration.TestEnum v)  { this.type = v; }
      public void setType (String v)  { this.type = de.mss.xml2class.enumeration.TestEnum.getByApiValue(v); }

   @Override
   public java.util.Map<String,String> doLogging() {
      java.util.Map<String, String> ret = new java.util.HashMap<>();
      ret = de.mss.utils.logging.LoggingUtil.addLogging("Masterfoo", this.masterfoo, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("Buffer", this.buffer, ret);
      ret = de.mss.utils.logging.LoggingUtil.addLogging("Type", this.type, ret);

      if(de.mss.utils.logging.Logable.class.isAssignable(super.getClass())) {
         ret.addAll(super.doLogging());
      } else {
         ret = de.mss.utils.logging.LoggingUtil.addLogging("Superclass", super.toString(), ret);
      }
      return ret;
   }

   @Override
   public String toString() {
      return de.mss.utils.logging.LoggingUtil.getLogString(doLogging());
   }
   
   
   @Override
   public void checkRequiredFields() throws de.mss.utils.exception.MssException {
      super.checkRequiredFields();

      if (this.masterfoo == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "masterfoo must not be null");
      }

   }

   private String writeBuffer() {
      StringBuilder sb = new StringBuilder("size {" + this.buffer.length + "} ");
      for(int i=0; i<this.buffer.length; i++) {
         sb.append("[" + i + "] {" + this.buffer[i] + "} ");
      }
      return sb.toString();
   }




}
