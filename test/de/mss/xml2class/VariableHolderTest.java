package de.mss.xml2class;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VariableHolderTest {

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


   @BeforeEach
   public void setUp() throws Exception {
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
      assertEquals("Anotation", this.classUnderTest.getAnnotation());
      assertEquals("a comment", this.classUnderTest.getComment());
      assertEquals("yyMMdd", this.classUnderTest.getDateFormat());
      assertEquals("variable", this.classUnderTest.getName());
      assertEquals("Integer", this.classUnderTest.getType());
      assertEquals("123", this.classUnderTest.getValue());

      assertEquals("variable", this.classUnderTest.getDeclarationName());
      assertEquals("variable", this.classUnderTest.getFieldName());
      assertEquals("variable = 123", this.classUnderTest.getFieldWithValue());
      assertEquals("Variable", this.classUnderTest.getMethodName());
      assertEquals("this.variable.toString()", this.classUnderTest.getPrintValue());

      assertFalse(this.classUnderTest.isRequired());
      assertFalse(this.classUnderTest.isVector());
      assertFalse(this.classUnderTest.isMap());
      assertFalse(this.classUnderTest.isSimpleArray());
      assertFalse(this.classUnderTest.isList());
   }


   @Test
   public void testBigDecimal() {
      this.classUnderTest.setType("java.math.BigDecimal");
      assertEquals("this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testBigInteger() {
      this.classUnderTest.setType("java.math.BigInteger");
      assertEquals("this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testBoolean() {
      this.classUnderTest.setType("Boolean");
      assertEquals("this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testDate() {
      this.classUnderTest.setType("java.util.Date");
      this.classUnderTest.setDateFormat("yyyy-MM-dd");
      assertEquals("new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(this.variable)", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testDouble() {
      this.classUnderTest.setType("Double");
      assertEquals("this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testFirstUpper() {
      this.classUnderTest.setName("Variable");
      assertEquals("Variable", this.classUnderTest.getMethodName());
   }


   @Test
   public void testFloat() {
      this.classUnderTest.setType("Float");
      assertEquals("this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testInteger() {
      this.classUnderTest.setType("Integer");
      assertEquals("this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testList() {
      this.classUnderTest.setType("java.util.List<String>");
      assertEquals("writeVariable()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testMap() {
      this.classUnderTest.setType("java.util.Map<String, String>");
      assertEquals("writeVariable()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testNameIsNull() {
      this.classUnderTest.setName(null);

      assertNull(this.classUnderTest.getDeclarationName());
      assertNull(this.classUnderTest.getMethodName());
      assertNull(this.classUnderTest.getFieldName());
   }


   @Test
   public void testOtherType() {
      this.classUnderTest.setType("de.mss.test.MyType");
      assertEquals("this.variable.toString()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleArray() {
      this.classUnderTest.setType("long[]");
      assertEquals("writeVariable()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleBoolean() {
      this.classUnderTest.setType("boolean");
      assertEquals("this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleByte() {
      this.classUnderTest.setType("byte");
      assertEquals("this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleDouble() {
      this.classUnderTest.setType("double");
      assertEquals("this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleFloat() {
      this.classUnderTest.setType("float");
      assertEquals("this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleInt() {
      this.classUnderTest.setType("int");
      assertEquals("this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleLong() {
      this.classUnderTest.setType("long");
      assertEquals("this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testSimpleShort() {
      this.classUnderTest.setType("short");
      assertEquals("this.variable", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testString() {
      this.classUnderTest.setType("String");
      assertEquals("this.variable", this.classUnderTest.getPrintValue());
      this.classUnderTest.setName("password");
      assertEquals("\"****\"", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testToLowerOnce() {
      this.classUnderTest.setName("Variable");
      assertEquals("variable", this.classUnderTest.getFieldName());
      assertEquals("variable", this.classUnderTest.getFieldName());
   }


   @Test
   public void testToUpperOnce() {
      assertEquals("Variable", this.classUnderTest.getMethodName());
      assertEquals("Variable", this.classUnderTest.getMethodName());
   }


   @Test
   public void testVector() {
      this.classUnderTest.setType("java.util.Vector<String>");
      assertEquals("writeVariable()", this.classUnderTest.getPrintValue());
   }


   @Test
   public void testWriteList() {
      this.classUnderTest.setType("java.util.List<String>");
      //@formatter:off
      final String exp = "   private String writeVariable() {\n"
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
      final String exp = "   private String writeVariable() {\n"
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
      final String exp = "   private String writeVariable() {\n"
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
      final String exp = "   private String writeVariable() {\n"
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
      final String exp = "   private String writeVariable() {\n"
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
      final String exp = "   private String writeVariable() {\n"
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
      final String exp = "   private String writeVariable() {\n"
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
      final String exp = "   private String writeVariable() {\n"
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
