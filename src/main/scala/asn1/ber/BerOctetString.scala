package asn1.ber

class BerOctetString(classAndPc: ClassAndPC, tag: Int, val value: String) extends DataValue(classAndPc, tag) {
  def this(value: String) = this(ClassAndPC(Ber.Universal), Ber.OctetString, value)
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


  def decode(classAndPc: ClassAndPC, tag: Int, valueOctets: Seq[Byte]): DataValue =
    new BerOctetString(classAndPc, tag, valueOctets.map(_.toChar).mkString)

  def decode(classAndPc: ClassAndPC, valueOctets: Seq[Byte]): DataValue =
    decode(classAndPc, Ber.OctetString, valueOctets)

}
