package com.wedul.pdf;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactoryImp;
import com.itextpdf.text.pdf.BaseFont;

class DefaultFontProvider extends FontFactoryImp {
 private String _default;
 public DefaultFontProvider(String def) {
  _default = def;
 }

 // I believe this is the correct override, but there are quite a few others.
 public Font getFont(String fontname,String encoding, boolean embedded, float size,int style, BaseColor color) {
  try {
   //한글 깨짐을 방지 하기위한 폰트 세팅
   return new Font(BaseFont.createFont(_default, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 9, style, BaseColor.BLACK);
  } catch (DocumentException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return null;
 }

}