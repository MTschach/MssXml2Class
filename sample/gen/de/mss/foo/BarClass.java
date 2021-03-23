package de.mss.foo;

public class BarClass extends java.math.BigDecimal {
   




   /**  */
   
   private java.util.Date masterfoo;
   

   /**  */
   
   private java.util.List<de.mss.test.TestKlasse> list;
   

   /**  */
   @HeaderParam(value = "list1")
   private java.util.List<String> list1;
   

   public BarClass () {
      super();
   }
   

   public java.util.Date getMasterfoo () { return this.masterfoo; }
   

   public java.util.List<de.mss.test.TestKlasse> getList () { return this.list; }
   

   public java.util.List<String> getList1 () { return this.list1; }
   


   public void setMasterfoo (java.util.Date v) { this.masterfoo = v; }
   

   public void setList (java.util.List<de.mss.test.TestKlasse> v) { this.list = v; }
   

   public void setList1 (java.util.List<String> v) { this.list1 = v; }
   


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(getClass().getName() + "[ ");

      if (this.masterfoo != null)
         sb.append("Masterfoo {" + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(this.masterfoo) + "} ");

      if (this.list != null)
         sb.append("List {" + writeList() + "} ");

      if (this.list1 != null)
         sb.append("List1 {" + writeList1() + "} ");

      sb.append(super.toString());
      sb.append("] ");
      return sb.toString();
   }




   public String writeList() {
      StringBuilder sb = new StringBuilder("size {" + this.list.size() + "} ");
      for(int i=0; i<this.list.size(); i++) {
         if (this.list.get(i) != null) sb.append("[" + i + "] {" + this.list.get(i).toString() + "} ");
      }
      return sb.toString();
   }




   public String writeList1() {
      StringBuilder sb = new StringBuilder("size {" + this.list1.size() + "} ");
      for(int i=0; i<this.list1.size(); i++) {
         if (this.list1.get(i) != null) sb.append("[" + i + "] {" + this.list1.get(i) + "} ");
      }
      return sb.toString();
   }





public boolean isBar() { return this.masterfoo != null; }

}
