# An ASN.1 Basic Encoding Rules library in Scala

This library decodes ASN.1 data values encoded using BER into objects that can be used by programs, e.g. an LDAP client or server.
 
**This library is not complete.** I am writing it as a way of learning Scala and functional programming.

Use it at your own risk!

## Design concepts

BER specifies that each data value comprises three parts:

1. One or more identifier octets.
2. One or length octets.
3. Octets containing the encoded value.

### Identifier octets

The identifier octets specify information about:
 
 - the class of the data value (highest two bits of the first octet)
 - whether it is primitive or constructed (next highest bit of the first octet)
 - an integer tag that specifies what kind of value is encoded
 
If the tag is less than 31 it is in the lowest five bits of the first octet. This means there is only one identifier octet.

**Currently the library only supports a single identifier octet: i.e. tag values up to 30.**

### Length octets

The length octets specify how many following octets contain the encoded value.

For a constructed data type, the length is the sum of the lengths of all contained data values, including their identifier and length octets.

**Currently the library only supports a single length octet: i.e. lengths up to 126.**

### Value octets

The value that is encoded, according to rules specified in http://www.itu.int/ITU-T/studygroups/com17/languages/X.690-0207.pdf.
