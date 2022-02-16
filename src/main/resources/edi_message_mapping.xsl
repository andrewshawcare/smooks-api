<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:edi="http://www.milyn.org/schema/edi-message-mapping-1.6.xsd"
    version="3.0"
>
    <xsl:template match="edi:edimap">
        <html>
            <head>
                <style>
                    body {
                        font-family: sans-serif;
                    }
                    dt {
                        margin-top: 1rem;
                        font-weight: bold;
                    }
                    dd {
                        margin: 0;
                    }
                </style>
            </head>
            <body>
                <h1><xsl:value-of select="edi:description/@name" /></h1>
                <h2>Delimiters</h2>
                <dl>
                    <dt>Segment</dt>
                    <dd><xsl:value-of select="edi:delimiters/@segment"/></dd>
                    <dt>Field</dt>
                    <dd><xsl:value-of select="edi:delimiters/@field"/></dd>
                    <dt>Component</dt>
                    <dd><xsl:value-of select="edi:delimiters/@component"/></dd>
                    <dt>Sub-component</dt>
                    <dd><xsl:value-of select="edi:delimiters/@sub-component"/></dd>
                </dl>
                <h2>Segments</h2>
                <xsl:for-each select="edi:segments/edi:segment">
                    <h3><dd><xsl:value-of select="@xmltag" /></dd></h3>
                    <dl>
                        <dt>Segment code</dt>
                        <dd><xsl:value-of select="@segcode" /></dd>
                        <dt>Description</dt>
                        <dd><xsl:value-of select="edi:documentation" /></dd>
                        <dt>Fields</dt>
                        <dd>
                            <ol>
                                <xsl:for-each select="edi:field">
                                    <li>
                                        <dl>
                                            <dt>Name</dt>
                                            <dd><xsl:value-of select="@xmltag" /></dd>
                                            <dt>Data type</dt>
                                            <dd><xsl:value-of select="@dataType" /></dd>
                                            <dt>Data type parameters</dt>
                                            <dd><xsl:value-of select="@dataTypeParameters" /></dd>
                                            <dt>Documentation</dt>
                                            <dd><xsl:value-of select="edi:documentation" /></dd>
                                        </dl>
                                    </li>
                                </xsl:for-each>
                            </ol>
                        </dd>
                    </dl>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:transform>