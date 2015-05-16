package asn1.ber

import org.scalatest.FlatSpec

class BerNullTest extends FlatSpec {

   "BerNull" should "be decoded correctly" in {
     assert(Ber.decode(Seq(5, 0)) == (BerNull, Seq()))
   }

  "BerEndOfContent" should "be decoded correctly" in {
     assert(Ber.decode(Seq(0, 0)) == (BerEndOfContent, Seq()))
  }

 }
