package asn1.ber

/**
 * Base class for data values.
 *
 * @param identifier identifier: class, primitive/constructed bit and tag
 *                   of the data value
 */
abstract class DataValue(val identifier: Identifier) {

  def contentBytes: Seq[Byte]
  def toBytes: Seq[Byte] = identifier.toBytes ++ contentBytes

}

/**
 * Object for decoding and encoding data values using Basic Encoding Rules.
 */
object Ber {

  val Universal: Byte       = 0x00
  val Constructed: Byte     = 0x20
  val Application: Byte     = 0x40
  val AppConstructed:  Byte = 0x60
  val ContextSpecific: Byte = 0x80.toByte
  val Private: Byte         = 0xC0.toByte

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
  def decodeId(idOctets: Seq[Byte]): (Identifier, Seq[Byte]) = {
    val firstOctet = unsigned(idOctets.head)
    (Identifier((firstOctet & 0xE0).toByte, firstOctet & 0x1F), idOctets.tail)
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
   * @param identifier Class and primitive/constructed information from the identifier octet(s)
   * @param valueOctets Octets containing only the value
   * @return The decoded data value
   */
  def decodeValue(identifier: Identifier, valueOctets: Seq[Byte]): DataValue = {
    if (identifier.isConstructed)
      // Constructed data value.
      BerConstructed.decode(identifier, valueOctets)
    else if (identifier.isUniversal)
      // Universal, primitive data value.
      identifier.tag match {
        case EndOfContent => BerEndOfContent
        case Boolean => BerBoolean.decode(valueOctets)
        case Integer => BerInteger.decode(identifier, valueOctets)
        case OctetString => BerOctetString.decode(identifier, valueOctets)
        case Null => BerNull
        case Enumerated => BerEnumerated.decode(identifier, valueOctets)
        case _ => BerBytes(identifier, valueOctets)
      }
    else
      BerBytes(identifier, valueOctets)
  }

  /**
   * Decodes a BER-encoded data value.
   *
   * @param octets Octets containing at least one complete data value
   * @return The decoded data value and any remaining octets
   */
  def decode(octets: Seq[Byte]): (DataValue, Seq[Byte]) = {
    val (identifier, idTail) = decodeId(octets)
    val (length, lengthTail) = decodeLength(idTail)
    if (length > lengthTail.length)
      throw new IllegalArgumentException("Insufficient octets provided for specified length")
    val (valueOctets, remainder) = lengthTail.splitAt(length)
    (decodeValue(identifier, valueOctets), remainder)
  }

}