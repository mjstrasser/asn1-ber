package asn1.ber

class BerInteger(classAndPc: ClassAndPC, tag: Int, val value: BigInt) extends DataValue(classAndPc, tag) {
  def this(value: BigInt) = this(ClassAndPC(Ber.Universal), Ber.Integer, value)
  override def equals(other: Any) = other match {
    case that: BerInteger => this.tag == that.tag && this.value == that.value
    case _ => false
  }
  override def hashCode = 41 * (tag.hashCode + 41) + value.hashCode
  def canEqual(other: Any) = other.isInstanceOf[BerInteger]
  override def toString = s"BerInteger($value)"
  override def contentBytes = {

    // Number of bytes required to represent this integer.
    val len = value.bitLength / 8 + 1
    // Padding for positive, negative values. See section 8.3 of ITU-T X.690:
    // http://www.itu.int/ITU-T/studygroups/com17/languages/X.690-0207.pdf
    // For positive integers, if most significant bit in an octet is set to one,
    // pad the result (otherwise it's decoded as a negative value).
    val contentLength = if ((value > 0 && (value & (0x80 << (len - 1) * 8)) > 0) ||
      // And for negative integers, pad if the most significant bit in the octet
      // is not set to one (otherwise, it's decoded as positive value).
      (value < 0 && (value & (0x80 << (len - 1) * 8)) == BigInt(0)))
      len + 1
    else
      len

    // This code is adapted from the Ruby version in the net-ldap library.
    def contentsOctets = {
      // Gather octets from length - 1 to zero.
      def octets(pos: Int): Seq[Byte] = {
        val octet: Seq[Byte] = Seq(((value >> (pos * 8)) & 0xFF).toByte)
        if (pos == 0)
          octet
        else
          octet ++ octets(pos - 1)
      }
      octets(contentLength - 1)
    }

    contentLength.toByte +: contentsOctets
  }
}

object BerInteger {

  def apply(value: BigInt) = new BerInteger(value)

  // This code is adapted from the Ruby version in the net-ldap library.
  def decodeValue(valueOctets: Seq[Byte]): BigInt = {
    // First bit indicates if the integer is negative.
    val negative = (valueOctets.head & 0x80) != 0x00
    // Read bytes, accumulating an unsigned value.
    def readOctet(octets: Seq[Byte], acc: BigInt): BigInt = {
      if (octets.length == 0)
        acc
      else {
        val nextUnsignedByte = octets.head & 0xFF
        val nextByte = if (negative) 255 - nextUnsignedByte else nextUnsignedByte
        readOctet(octets.tail, (acc << 8) + nextByte)
      }
    }
    val unsigned = readOctet(valueOctets, 0)
    // Correct for sign and return the value.
    if (negative) (unsigned + 1) * -1 else unsigned
  }

  def decode(classAndPC: ClassAndPC, valueOctets: Seq[Byte]): DataValue =
    new BerInteger(classAndPC, Ber.Integer, decodeValue(valueOctets))
}
