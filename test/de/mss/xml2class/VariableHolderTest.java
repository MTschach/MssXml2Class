package de.mss.xml2class;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

public class VariableHolderTest extends TestCase {

   private VariableHolder classUnderTest;

   private Map<String, ClassHolder> getSubClasses(boolean hasRequiredSubtypes) {
      final Map<String, ClassHolder> clazzes = new HashMap<>();

      if (hasRequiredSubtypes) {
         final ClassHolder ch = new ClassHolder();
         final VariableHolder v = new VariableHolder();
         v.setName("subType");
         v.setRequired(true);
         v.setType("String");
         ch.addVariable(v);
         clazzes.put("ClassWithSubtypes", ch);
      }

      return clazzes;
   }


   @Override
   public void setUp() throws Exception {
      super.setUp();

      this.classUnderTest = new VariableHolder();
      this.classUnderTest.setAnnotation("Anotation");
      this.classUnderTest.setComment("a comment");
      this.classUnderTest.setName("variable");
      this.classUnderTest.setType("Integer");
      this.classUnderTest.setValue("123");
   }


   @Test
   public void test() {
      this.classUnderTest.setDateFormat("yyMMdd");
      assertEquals("Annotation", "Anotation", this.classUnderTest.getAnnotation());
      assertEquals("Comment", "a comment", this.classUnderTest.getComment());
      assertEquals("DateFormat", "yyMMdd", this.classUnderTest.getDateFormat());
      assertEquals("Name", "variable", this.classUnderTest.getName());
      assertEquals("Type", "Integer", this.classUnderTest.getType());
      assertEquals("Value", "123", this.classUnderTest.getValue());

      assertEquals("Declaration name", "variable", this.classUnderTest.getDeclarationName());
      assertEquals("Field name", "variable", this.classUnderTest.getFieldName());
      assertEquals("Field with value", "variable = 123", this.classUnderTest.getFieldWithValue());
      assertEquals("Method name", "Variable", this.classUnderTest.getMethodName());
      assertEquals("PrintValue", "this.variable.toString()", this.classUnderTest.getPrintValue());

      assertFalse("Required", this.classUnderTest.isRequired());
      assertFalse("is Vector", this.classUnderTest.isVector());
      assertFalse("is Map", this.classUnderTest.isMap());
      assertFalse("is simple array", this.classUnderTest.isSimpleArray());
      assertFalse("is List", this.classUnderTest.isList());
   }


   @Test
   public void testBigDecimal() {
      this.classUnderTest.setType("java.math.BigDecimal");
      assertEquals("Print Value", "this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testBigInteger() {
      this.classUnderTest.setType("java.math.BigInteger");
      assertEquals("Print Value", "this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testBoolean() {
      this.classUnderTest.setType("Boolean");
      assertEquals("Print Value", "this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testDate() {
      this.classUnderTest.setType("java.util.Date");
      this.classUnderTest.setDateFormat("yyyy-MM-dd");
      assertEquals("Print Value", "new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(this.variable)", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testDouble() {
      this.classUnderTest.setType("Double");
      assertEquals("Print Value", "this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testFirstUpper() {
      this.classUnderTest.setName("Variable");
      assertEquals("Method name", "Variable", this.classUnderTest.getMethodName());
   }


   @Test
   public void testFloat() {
      this.classUnderTest.setType("Float");
      assertEquals("Print Value", "this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testInteger() {
      this.classUnderTest.setType("Integer");
      assertEquals("Print Value", "this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testList() {
      this.classUnderTest.setType("java.util.List<String>");
      assertEquals("Print Value", "writeVariable()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testMap() {
      this.classUnderTest.setType("java.util.Map<String, String>");
      assertEquals("Print Value", "writeVariable()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testNameIsNull() {
      this.classUnderTest.setName(null);

      assertNull("Declaration name", this.classUnderTest.getDeclarationName());
      assertNull("Method name", this.classUnderTest.getMethodName());
      assertNull("Field name", this.classUnderTest.getFieldName());
   }


   @Test
   public void testOtherType() {
      this.classUnderTest.setType("de.mss.test.MyType");
      assertEquals("Print Value", "this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleArray() {
      this.classUnderTest.setType("long[]");
      assertEquals("Print Value", "writeVariable()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleBoolean() {
      this.classUnderTest.setType("boolean");
      assertEquals("Print Value", "this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleByte() {
      this.classUnderTest.setType("byte");
      assertEquals("Print Value", "this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleDouble() {
      this.classUnderTest.setType("double");
      assertEquals("Print Value", "this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleFloat() {
      this.classUnderTest.setType("float");
      assertEquals("Print Value", "this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleInt() {
      this.classUnderTest.setType("int");
      assertEquals("Print Value", "this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleLong() {
      this.classUnderTest.setType("long");
      assertEquals("Print Value", "this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleShort() {
      this.classUnderTest.setType("short");
      assertEquals("Print Value", "this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testString() {
      this.classUnderTest.setType("String");
      assertEquals("Print Value", "this.variable", this.classUnderTest.getPrintValue());
      this.classUnderTest.setName("password");
      assertEquals("Print Value", "\"****\"", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testToLowerOnce() {
      this.classUnderTest.setName("Variable");
      assertEquals("Field name", "variable", this.classUnderTest.getFieldName());
      assertEquals("Field name", "variable", this.classUnderTest.getFieldName());
   }


   @Test
   public void testToUpperOnce() {
      assertEquals("Method name", "Variable", this.classUnderTest.getMethodName());
      assertEquals("Method name", "Variable", this.classUnderTest.getMethodName());
   }


   @Test
   public void testVector() {
      this.classUnderTest.setType("java.util.Vector<String>");
      assertEquals("Print Value", "writeVariable()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testWriteList() {
      this.classUnderTest.setType("java.util.List<String>");
      //@formatter:off
      final String exp = "   public String writeVariable() {\n"
                 + "      StringBuilder sb = new StringBuilder(\"size {\" + this.variable.size() + \"} \");\n"
                 + "      for(int i=0; i<this.variable.size(); i++) {\n"
                 + "         if (this.variable.get(i) != null) sb.append(\"[\" + i + \"] {\" + this.variable.get(i) + \"} \");\n"
                 + "      }\n"
                 + "      return sb.toString();\n"
                 + "   }\n\n\n";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }


   @Test
   public void testWriteListInt() {
      this.classUnderTest.setType("java.util.List<int>");
      //@formatter:off
      final String exp = "   public String writeVariable() {\n"
                 + "      StringBuilder sb = new StringBuilder(\"size {\" + this.variable.size() + \"} \");\n"
                 + "      for(int i=0; i<this.variable.size(); i++) {\n"
                 + "         sb.append(\"[\" + i + \"] {\" + this.variable.get(i) + \"} \");\n"
                 + "      }\n"
                 + "      return sb.toString();\n"
                 + "   }\n\n\n";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }


   @Test
   public void testWriteMap() {
      this.classUnderTest.setType("java.util.Map<String, Integer>");
      //@formatter:off
      final String exp = "   public String writeVariable() {\n"
                 + "      StringBuilder sb = new StringBuilder(\"size {\" + this.variable.size() + \"} \");\n"
                 + "      for(java.util.Map.Entry<String, Integer> e : this.variable.entrySet()) {\n"
                 + "         if (e.getValue() != null) sb.append(\"[\" + e.getKey() + \"] {\" + e.getValue().toString() + \"} \");\n"
                 + "      }\n"
                 + "      return sb.toString();\n"
                 + "   }\n";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }


   @Test
   public void testWriteMapInt() {
      this.classUnderTest.setType("java.util.Map<String,int>");
      //@formatter:off
      final String exp = "   public String writeVariable() {\n"
                 + "      StringBuilder sb = new StringBuilder(\"size {\" + this.variable.size() + \"} \");\n"
                 + "      for(java.util.Map.Entry<String,int> e : this.variable.entrySet()) {\n"
                 + "         sb.append(\"[\" + e.getKey() + \"] {\" + e.getValue() + \"} \");\n"
                 + "      }\n"
                 + "      return sb.toString();\n"
                 + "   }\n";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }


   @Test
   public void testWriteOther() {
      this.classUnderTest.setType("OtherClass");
      //@formatter:off
      final String exp = "";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }


   @Test
   public void testWriteRequiredCheckList() {
      this.classUnderTest.setRequired(true);
      this.classUnderTest.setType("java.util.List<Integer>");
      //@formatter:off
      final String exp = "if (this.variable == null) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be null\");\n"
                 + "      }\n"
                 + "      if (this.variable.isEmpty()) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be empty\");\n"
                 + "      }\n"
                 + "      for (Integer e : this.variable) {\n"
                 + "         if (e == null) {\n"
                 + "            throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable element must not be null\");\n"
                 + "         }\n"
                 + "      }\n";
      //@formatter:on
      assertEquals(exp, this.classUnderTest.writeRequiredCheck(getSubClasses(false)));
   }


   @Test
   public void testWriteRequiredCheckMap() {
      this.classUnderTest.setRequired(true);
      this.classUnderTest.setType("java.util.Map<Integer,String>");
      //@formatter:off
      final String exp = "if (this.variable == null) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be null\");\n"
                 + "      }\n"
                 + "      if (this.variable.isEmpty()) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be empty\");\n"
                 + "      }\n";
      //@formatter:on
      assertEquals(exp, this.classUnderTest.writeRequiredCheck(getSubClasses(false)));
   }


   @Test
   public void testWriteRequiredCheckNotRequired() {
      assertEquals("", this.classUnderTest.writeRequiredCheck(null));
   }


   @Test
   public void testWriteRequiredCheckSimpleArray() {
      this.classUnderTest.setRequired(true);
      this.classUnderTest.setType("int[]");
      //@formatter:off
      final String exp = "if (this.variable == null) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be null\");\n"
                 + "      }\n"
                 + "      if (this.variable.length == 0) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be empty\");\n"
                 + "      }\n"
                 + "      for (int e : this.variable) {\n"
                 + "         if (e == null) {\n"
                 + "            throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable element must not be null\");\n"
                 + "         }\n"
                 + "      }\n";
      //@formatter:on
      assertEquals(exp, this.classUnderTest.writeRequiredCheck(getSubClasses(false)));
   }


   @Test
   public void testWriteRequiredCheckSimpleType() {
      this.classUnderTest.setRequired(true);
      this.classUnderTest.setType("int");
      assertEquals("", this.classUnderTest.writeRequiredCheck(null));
   }


   @Test
   public void testWriteRequiredCheckSubtypes() {
      this.classUnderTest.setRequired(true);
      this.classUnderTest.setType("ClassWithSubtypes");
      //@formatter:off
      final String exp = "if (this.variable == null) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be null\");\n"
                 + "      }\n"
                 + "      this.variable.checkRequiredFields();\n";
      //@formatter:on
      assertEquals(exp, this.classUnderTest.writeRequiredCheck(getSubClasses(true)));
   }


   @Test
   public void testWriteRequiredCheckVector() {
      this.classUnderTest.setRequired(true);
      this.classUnderTest.setType("java.util.Vector<Integer>");
      //@formatter:off
      final String exp = "if (this.variable == null) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be null\");\n"
                 + "      }\n"
                 + "      if (this.variable.isEmpty()) {\n"
                 + "         throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable must not be empty\");\n"
                 + "      }\n"
                 + "      for (Integer e : this.variable) {\n"
                 + "         if (e == null) {\n"
                 + "            throw new de.mss.utils.exception.MssException(de.mss.net.exception.ErrorCodes.ERROR_REQUIRED_FIELD_MISSING, \"variable element must not be null\");\n"
                 + "         }\n"
                 + "      }\n";
      //@formatter:on
      assertEquals(exp, this.classUnderTest.writeRequiredCheck(getSubClasses(false)));
   }


   @Test
   public void testWriteSimpleArray() {
      this.classUnderTest.setType("Integer[]");
      //@formatter:off
      final String exp = "   public String writeVariable() {\n"
                 + "      StringBuilder sb = new StringBuilder(\"size {\" + this.variable.length + \"} \");\n"
                 + "      for(int i=0; i<this.variable.length; i++) {\n"
                 + "         if (this.variable[i] != null) sb.append(\"[\" + i + \"] {\" + this.variable[i].toString() + \"} \");\n"
                 + "      }\n"
                 + "      return sb.toString();\n"
                 + "   }\n\n\n";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }


   @Test
   public void testWriteSimpleArrayInt() {
      this.classUnderTest.setType("int[]");
      //@formatter:off
      final String exp = "   public String writeVariable() {\n"
                 + "      StringBuilder sb = new StringBuilder(\"size {\" + this.variable.length + \"} \");\n"
                 + "      for(int i=0; i<this.variable.length; i++) {\n"
                 + "         sb.append(\"[\" + i + \"] {\" + this.variable[i] + \"} \");\n"
                 + "      }\n"
                 + "      return sb.toString();\n"
                 + "   }\n\n\n";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }


   @Test
   public void testWriteVector() {
      this.classUnderTest.setType("java.util.Vector<String>");
      //@formatter:off
      final String exp = "   public String writeVariable() {\n"
                 + "      StringBuilder sb = new StringBuilder(\"size {\" + this.variable.size() + \"} \");\n"
                 + "      for(int i=0; i<this.variable.size(); i++) {\n"
                 + "         if (this.variable.get(i) != null) sb.append(\"[\" + i + \"] {\" + this.variable.get(i) + \"} \");\n"
                 + "      }\n"
                 + "      return sb.toString();\n"
                 + "   }\n\n\n";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }


   @Test
   public void testWriteVectorBoolean() {
      this.classUnderTest.setType("java.util.Vector<boolean>");
      //@formatter:off
      final String exp = "   public String writeVariable() {\n"
                 + "      StringBuilder sb = new StringBuilder(\"size {\" + this.variable.size() + \"} \");\n"
                 + "      for(int i=0; i<this.variable.size(); i++) {\n"
                 + "         sb.append(\"[\" + i + \"] {\" + this.variable.get(i) + \"} \");\n"
                 + "      }\n"
                 + "      return sb.toString();\n"
                 + "   }\n\n\n";
      //@formatter:on

      assertEquals(exp, this.classUnderTest.writeSpecialMethods());
   }
}
