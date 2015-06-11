package asn1.ber

import org.scalatest.FlatSpec

class BerConstructedTest extends FlatSpec {

  val seqBytes = Seq(48, 6,
    1, 1, 0,
    2, 1, 12).map(_.toByte)
  val seqBer = BerSequence(
    BerBoolean(value = false),
    BerInteger(12)
  )

  val ldapBindRequestBytes = Seq(
    48, 24,
    2, 1, 1,
    96, 19,
    2, 1, 3,
    4, 9, 111, 61, 84, 101, 108, 115, 116, 114, 97,
    128, 3, 101, 115, 115).map(_.toByte)
  val ldapBindRequestBer = BerSequence(
    BerInteger(1),
    BerConstructed(Identifier(Ber.AppConstructed, 0),
      BerInteger(3),
      BerOctetString("o=Telstra"),
      BerBytes(Identifier(Ber.ContextSpecific, 0), "ess".getBytes)
    )
  )

  "BerConstructed" should "decode a sequence correctly" in {
    assert(Ber.decode(seqBytes) == (seqBer, Seq()))
    assert(Ber.decode(ldapBindRequestBytes) == (ldapBindRequestBer, Seq()))
  }

  it should "encode a sequence correctly" in {
    assert(seqBer.toBytes == seqBytes)
    assert(ldapBindRequestBer.toBytes == ldapBindRequestBytes)
  }

 }
