package de.mss.xml2class;

import org.junit.Test;

import junit.framework.TestCase;

public class ConstantHolderTest extends TestCase {

   private ConstantHolder classUnderTest;

   @Override
   public void setUp() throws Exception {
      super.setUp();

      this.classUnderTest = new ConstantHolder();
      this.classUnderTest.setComment("a simple comment");
      this.classUnderTest.setName("constant_name");
      this.classUnderTest.setType("Integer");
      this.classUnderTest.setValue("null");
   }


   @Test
   public void testEmtpyValue() {
      this.classUnderTest.setValue("");
      assertEquals("Comment", "a simple comment", this.classUnderTest.getComment());
      assertEquals("Name", "constant_name", this.classUnderTest.getName());
      assertEquals("Type", "Integer", this.classUnderTest.getType());
      assertEquals("Value", "", this.classUnderTest.getValue());
      assertEquals("Declaration name", "CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("Field with value", "CONSTANT_NAME", this.classUnderTest.getFieldWithValue());
   }


   @Test
   public void testNullValue() {
      this.classUnderTest.setValue(null);
      assertEquals("Comment", "a simple comment", this.classUnderTest.getComment());
      assertEquals("Name", "constant_name", this.classUnderTest.getName());
      assertEquals("Type", "Integer", this.classUnderTest.getType());
      assertNull("Value is null", this.classUnderTest.getValue());
      assertEquals("Declaration name", "CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("Field with value", "CONSTANT_NAME", this.classUnderTest.getFieldWithValue());
   }


   @Test
   public void testStringNullValue() {
      this.classUnderTest.setType("String");
      assertEquals("Comment", "a simple comment", this.classUnderTest.getComment());
      assertEquals("Name", "constant_name", this.classUnderTest.getName());
      assertEquals("Type", "String", this.classUnderTest.getType());
      assertEquals("Value", "null", this.classUnderTest.getValue());
      assertEquals("Declaration name", "CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("Field with value", "CONSTANT_NAME = null", this.classUnderTest.getFieldWithValue());
   }


   @Test
   public void testStringValue() {
      this.classUnderTest.setType("String");
      this.classUnderTest.setValue("a value");
      assertEquals("Comment", "a simple comment", this.classUnderTest.getComment());
      assertEquals("Name", "constant_name", this.classUnderTest.getName());
      assertEquals("Type", "String", this.classUnderTest.getType());
      assertEquals("Value", "a value", this.classUnderTest.getValue());
      assertEquals("Declaration name", "CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("Field with value", "CONSTANT_NAME = \"a value\"", this.classUnderTest.getFieldWithValue());
   }


   @Test
   public void testValue() {
      this.classUnderTest.setValue("123");
      assertEquals("Comment", "a simple comment", this.classUnderTest.getComment());
      assertEquals("Name", "constant_name", this.classUnderTest.getName());
      assertEquals("Type", "Integer", this.classUnderTest.getType());
      assertEquals("Value", "123", this.classUnderTest.getValue());
      assertEquals("Declaration name", "CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("Field with value", "CONSTANT_NAME = 123", this.classUnderTest.getFieldWithValue());
   }

}
