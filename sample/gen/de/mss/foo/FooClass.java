package de.mss.foo;

public class FooClass extends java.math.BigDecimal {
   




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
   public String toString() {
      StringBuilder sb = new StringBuilder(getClass().getName() + "[ ");

      if (this.masterfoo != null)
         sb.append("Masterfoo {" + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(this.masterfoo) + "} ");

      if (this.buffer != null)
         sb.append("Buffer {" + writeBuffer() + "} ");

      if (this.type != null)
         sb.append("Type {" + this.type.toString() + "} ");

      sb.append(super.toString());
      sb.append("] ");
      return sb.toString();
   }

   @Override
   public void checkRequiredFields() throws de.mss.utils.exception.MssException {

      if (this.masterfoo == null) {
         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, "masterfoo must not be null");
      }


      

      

      super.checkRequiredFields();
   }




   public String writeBuffer() {
      StringBuilder sb = new StringBuilder("size {" + this.buffer.length + "} ");
      for(int i=0; i<this.buffer.length; i++) {
         sb.append("[" + i + "] {" + this.buffer[i] + "} ");
      }
      return sb.toString();
   }







}
