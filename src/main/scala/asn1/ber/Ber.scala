package asn1.ber

abstract class DataValue(val classAndPc: Byte, val tag: Int) {
  def toBytes: Seq[Byte]
}

object Ber {

  val Universal: Byte = 0x00
  val Constructed     = 0x20
  val Application     = 0x40
  val ContextSpecific = 0x80
  val Private         = 0xC0

  // Universal tag values.
  // - Primitive
  val EndOfContent    = 0
  val Boolean         = 1
  val Integer         = 2
  val OctetString     = 4
  val Null            = 5
  val Enumerated      = 10
  // - Constructed
  val Sequence        = 16
  val Set             = 17

  def unsigned(b: Byte) = (b & 0xFF).toByte

  /**
   * Decodes the identifier of a BER type.
   *
   * Currently only single-octet identifiers are supported.
   *
   * @param idOctets Octets from which the identifier is decoded
   * @return The class and P/C bits from the first octet, the tag number separate from those
   *         and remaining octets
   */
  def decodeId(idOctets: Seq[Byte]): (Byte, Int, Seq[Byte]) = {
    val firstOctet= unsigned(idOctets.head)
    val classAndPc = (firstOctet & 0xE0).toByte
    val tag = firstOctet & 0x1F
    (classAndPc, tag, idOctets.tail)
  }

  /**
   * Decodes the length of this BER-encoded data value.
   *
   * Currently only lengths up to 127 are supported.
   *
   * @param lengthOctets Octets from which the length is decoded
   * @return The decoded length and remaining octets
   */
  def decodeLength(lengthOctets: Seq[Byte]): (Int, Seq[Byte]) = {
    unsigned(lengthOctets.head) match {
      case l if l < 0x7F =>
        (l, lengthOctets.tail)
      case 0xFF =>
        throw new IllegalArgumentException("Invalid BER length 0xFF detected")
      case 0x80 =>
        throw new UnsupportedOperationException("Indeterminate content length is not supported")
      case _ =>
        throw new UnsupportedOperationException("Only single-byte lengths are currently supported")
    }
  }

  /**
   * Decodes the value octets of a BER-encoded data value.
   *
   * @param valueOctets Octets containing only the value
   * @param classAndPc Octet with the class and P/C bits from the identifier octet(s)
   * @param tag Tag of this value
   * @return The decoded data value
   */
  def decodeValue(valueOctets: Seq[Byte], classAndPc: Byte, tag: Int): DataValue = {
    if ((classAndPc & Constructed) != 0x00)
    // Constructed data value.
      ???
    else
    // Primitive data value.
      tag match {
        case Boolean => BerBoolean.decode(classAndPc, valueOctets)
//        case Integer => BerInteger.decode(classAndPc, valueOctets)
//        case Enumerated => BerEnumerated.decode(classAndPc, valueOctets)
//        case OctetString => BerOctetString.decode(classAndPc, valueOctets)
//        case Null => BerNull(classAndPc, valueOctets)
//        case _ => BerBytes(classAndPc, tag, valueOctets)
      }
  }

  /**
    * Decodes a BER-encoded data value.
   *
   * @param octets Octets containing at least one complete data value
   * @return The decoded data value and any remaining octets
   */
  def decode(octets: Seq[Byte]): (DataValue, Seq[Byte]) = {
    val (classAndPc, tag, idTail) = decodeId(octets)
    val (length, lengthTail) = decodeLength(idTail)
    val (valueOctets, remainder) = lengthTail.splitAt(length)
    (decodeValue(valueOctets, classAndPc, tag), remainder)
  }


  def constructed(tag: Int) = tag + Constructed
  def applicationId(tag: Int) = tag + Application
  def contextSpecific(tag: Int) = tag + ContextSpecific
}