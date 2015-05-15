package asn1.ber

import org.scalatest.FlatSpec

class BerEnumeratedTest extends FlatSpec {

   "BerEnumerated" should "decode integer values correctly" in {
     assert(Ber.decode(Seq(10, 1, 5)) == (BerEnumerated(5), Seq()))
   }

 }
