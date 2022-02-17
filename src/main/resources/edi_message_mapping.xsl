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
                        padding: 1rem;
                        line-height: 1.5rem;
                        font-family: sans-serif;
                        margin-top: -2.5rem;
                    }
                    h1, h2, h3, h4, h5, h6 {
                        margin-top: 3rem;
                    }
                    table {
                        border-collapse: collapse;
                    }
                    tr {
                        line-height: 1.5rem;
                    }
                    td > ol {
                        padding: 0;
                    }
                    th {
                        vertical-align: baseline;
                        text-align: left;
                        padding-right: 1rem;
                    }
                </style>
            </head>
            <body>
                <h1><xsl:value-of select="edi:description/@name" /></h1>
                <h2>Delimiters</h2>
                <table>
                    <tbody>
                        <tr>
                            <th>Segment</th>
                            <td>Line</td>
                        </tr>
                        <tr>
                            <th>Field</th>
                            <td><xsl:value-of select="edi:delimiters/@field"/></td>
                        </tr>
                        <tr>
                            <th>Component</th>
                            <td><xsl:value-of select="edi:delimiters/@component"/></td>
                        </tr>
                        <tr>
                            <th>Sub-component</th>
                            <td><xsl:value-of select="edi:delimiters/@sub-component"/></td>
                        </tr>
                    </tbody>
                </table>
                <h2>Segments</h2>
                <xsl:for-each select="edi:segments/edi:segment">
                    <h3><xsl:value-of select="@xmltag" /></h3>
                    <table>
                        <tbody>
                            <tr>
                                <th>Segment code</th>
                                <td><xsl:value-of select="@segcode" /></td>
                            </tr>
                            <tr>
                                <th>Documentation</th>
                                <td><xsl:value-of select="edi:documentation" /></td>
                            </tr>
                            <tr>
                                <th>Fields</th>
                                <td>
                                    <ol>
                                        <xsl:for-each select="edi:field">
                                            <li>
                                                <table>
                                                    <tbody>
                                                        <tr>
                                                            <th>Name</th>
                                                            <td><xsl:value-of select="@xmltag" /></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Data type</th>
                                                            <td><xsl:value-of select="@dataType" /></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Data type parameters</th>
                                                            <td><xsl:value-of select="@dataTypeParameters" /></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Documentation</th>
                                                            <td><xsl:value-of select="edi:documentation" /></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </li>
                                        </xsl:for-each>
                                    </ol>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:transform>