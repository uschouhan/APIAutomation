package com.angelone.tests.orderapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import com.angelone.api.utility.Helper;

public class GZIPDecompress {

  public static void main(String[] args) throws IOException {
//    String compressedString = "eJzclMtu2zoQhl/FmO1hCF50Xx3HkQujjdom7sIoCoG2WIEoRSki7UII/O4FK8ex2xTIJkAb7fSDM/PPzIe5B+vazbdSK+sg+3wP1+1aaVkW2wYyKFojAUHeCKUhg20nTdULSihJ4v9rr+JN2wCCqlMVZEAZ4ZwRGoQsSDjhgMBuetUZ0UjIYDGfLSbvllcYECirjJeKnPB0SnxOQPC1l7K8cwNkwDDxHyDotKzqB5kc5Uo46VQjrRNN5wMI4xeEX7B0QqIsSLIwwiwGBO1aq1o41fqCPnJrxpSH364VvldAYKQrO13VJ7UAwaMfQLDRrZVlL5yPTjEdvbjWCT8hmmA2KsbKUnRd3+6kn8x0J3tR+2HuRF82ovcZGSZsfCxdKRrnEwSYpqdORvngZHG7KMrl0Pna+d1WuQEQFJfzWXmp/RY/jrYBwUwradzD2/eNkXawj/phIZ8+5MXVzRQQXKva9+StrsaWS60a5aj3eSqwQ4FeVsoujJW9H+yVcHKpfuY8XUOcEZ6xCNM0jWj8Hwkz7mMLK+3QrFt9YAL26IXBM5USZnIjlP4uhslcGWHOGQz5/B9lkIU4PIcwpJg8H0JyfHyEMCA4fpUQJjR6GsKb+ezFIVzlt5PLafH29wsYsuQNoYSH5/TRv4w++gR9NMBxeH4Cj8rz6Ev4ryeQ4ih+jfTFfziBq/zWcwH7L/sfAQAA//91uy8L";
//    byte[] compressedBytes = compressedString.getBytes();
//
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    ByteArrayInputStream bais = new ByteArrayInputStream(compressedBytes);
//    GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
//
//    byte[] buffer = new byte[1024];
//    int len;
//    while ((len = gzipInputStream.read(buffer)) > 0) {
//      baos.write(buffer, 0, len);
//    }
//
//    gzipInputStream.close();
//    baos.close();
//    bais.close();
//
//    String decompressedString = baos.toString("UTF-8");
//    System.out.println(decompressedString);
	  
	  Helper.generateDeviceId();
  }
}
