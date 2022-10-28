package de.mss.xml2class;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConstantHolderTest {

   private ConstantHolder classUnderTest;

   @BeforeEach
   public void setUp() throws Exception {

      this.classUnderTest = new ConstantHolder();
      this.classUnderTest.setComment("a simple comment");
      this.classUnderTest.setName("constant_name");
      this.classUnderTest.setType("Integer");
      this.classUnderTest.setValue("null");
   }


   @Test
   public void testEmtpyValue() {
      this.classUnderTest.setValue("");
      assertEquals("a simple comment", this.classUnderTest.getComment());
      assertEquals("constant_name", this.classUnderTest.getName());
      assertEquals("Integer", this.classUnderTest.getType());
      assertEquals("", this.classUnderTest.getValue());
      assertEquals("CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("CONSTANT_NAME", this.classUnderTest.getFieldWithValue());
   }


   @Test
   public void testNullValue() {
      this.classUnderTest.setValue(null);
      assertEquals("a simple comment", this.classUnderTest.getComment());
      assertEquals("constant_name", this.classUnderTest.getName());
      assertEquals("Integer", this.classUnderTest.getType());
      assertNull(this.classUnderTest.getValue());
      assertEquals("CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("CONSTANT_NAME", this.classUnderTest.getFieldWithValue());
   }


   @Test
   public void testStringNullValue() {
      this.classUnderTest.setType("String");
      assertEquals("a simple comment", this.classUnderTest.getComment());
      assertEquals("constant_name", this.classUnderTest.getName());
      assertEquals("String", this.classUnderTest.getType());
      assertEquals("null", this.classUnderTest.getValue());
      assertEquals("CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("CONSTANT_NAME = null", this.classUnderTest.getFieldWithValue());
   }


   @Test
   public void testStringValue() {
      this.classUnderTest.setType("String");
      this.classUnderTest.setValue("a value");
      assertEquals("a simple comment", this.classUnderTest.getComment());
      assertEquals("constant_name", this.classUnderTest.getName());
      assertEquals("String", this.classUnderTest.getType());
      assertEquals("a value", this.classUnderTest.getValue());
      assertEquals("CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("CONSTANT_NAME = \"a value\"", this.classUnderTest.getFieldWithValue());
   }


   @Test
   public void testValue() {
      this.classUnderTest.setValue("123");
      assertEquals("a simple comment", this.classUnderTest.getComment());
      assertEquals("constant_name", this.classUnderTest.getName());
      assertEquals("Integer", this.classUnderTest.getType());
      assertEquals("123", this.classUnderTest.getValue());
      assertEquals("CONSTANT_NAME", this.classUnderTest.getDeclarationName());
      assertEquals("CONSTANT_NAME = 123", this.classUnderTest.getFieldWithValue());
   }

}
