package de.mss.xml2class;

import java.io.File;
import java.io.FilenameFilter;

public class TemplateFileNameFilter implements FilenameFilter {

   @Override
   public boolean accept(File dir, String name) {
      File f = new File(name);

      if (f.isDirectory())
         return true;

      return name.endsWith(".template");
   }

}
