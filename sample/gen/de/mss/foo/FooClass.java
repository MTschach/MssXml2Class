package de.mss.foo;

public class FooClass extends java.math.BigDecimal {
   




   /**  */
   
   private java.util.Date masterfoo;
   

   /**  */
   
   private byte[] buffer;
   

   public FooClass () {
      super();
   }
   

   public java.util.Date getMasterfoo () { return this.masterfoo; }
   

   public byte[] getBuffer () { return this.buffer; }
   


   public void setMasterfoo (java.util.Date v) { this.masterfoo = v; }
   

   public void setBuffer (byte[] v) { this.buffer = v; }
   


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(getClass().getName() + "[ ");

      if (this.masterfoo != null)
         sb.append("Masterfoo {" + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(this.masterfoo) + "} ");

      if (this.buffer != null)
         sb.append("Buffer {" + writeBuffer() + "} ");

      sb.append(super.toString());
      sb.append("] ");
      return sb.toString();
   }



   public String writeBuffer() {
      StringBuilder sb = new StringBuilder("size {" + this.buffer.length + "} ");
      for(int i=0; i<this.buffer.length; i++) {
         if (this.buffer[i] != null) sb.append("[" + i + "] {" + writeBuffer() + "} ");
      }
      return sb.toString();
   }






}
