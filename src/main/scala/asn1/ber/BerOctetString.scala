package asn1.ber

class BerOctetString(identifier: Identifier, val value: String) extends DataValue(identifier) {
  def this(value: String) = this(Identifier(Ber.Universal, Ber.OctetString), value)

  override def equals(other: Any) = other match {
    case that: BerOctetString => this.value == that.value
    case _ => false
  }
  override def hashCode = (value.hashCode + 41) * 41
  def canEqual(other: Any) = other.isInstanceOf[BerOctetString]
  // String interpolation does not work with embedded " characters.
  override def toString = "BerOctetString(\"" + value + "\")"
  override def contentBytes = {
    val bytes = value.toCharArray.map(_.toByte)
    if (bytes.length > 127)
      throw new IllegalStateException("String too long: maximum supported length is 127")
    bytes.length.toByte +: bytes
  }
}

object BerOctetString {

  def apply(value: String) = new BerOctetString(value)


  def decode(identifier: Identifier, valueOctets: Seq[Byte]): DataValue =
    new BerOctetString(identifier, valueOctets.map(_.toChar).mkString)

}
