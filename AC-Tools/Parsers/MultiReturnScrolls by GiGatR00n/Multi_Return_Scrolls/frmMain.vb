'=====================================================
'Parser.....:  Multi-Return Scrolls
'
'Author.....:  GiGatR00n v4.7.5.x
'Date.......:  ‎Saturday, ‎April ‎25, ‎2015, ‏‎6:02:15 AM
'
'Skype......:  GiGatR00n
'=====================================================



Imports System.Xml
Imports System.Xml.Serialization
Imports System.IO
Imports System.Text
Imports System.Xml.Schema


Public Class frmMain

    Private Sub btnClose_Click(sender As Object, e As EventArgs) Handles btnClose.Click
        End
    End Sub

    Private Sub btnParser_Click(sender As Object, e As EventArgs) Handles btnParser.Click


        '\\ Specifies the Type of Object to Serialize.
        '==============================================
        Dim ScrollsSerializer As New XmlSerializer(GetType(Multi_Return_Scrolls))
        Dim ScrollWriterSerializer As New XmlSerializer(GetType(item_multi_returns))
        Dim StringsSerializer As New XmlSerializer(GetType(strings))


        '\\ Create Stream Reader/Writer for XML Documents.
        '=================================================
        Dim StringsReader As New StreamReader(txtStrItem3Path.Text)
        Dim ScrollsReader As New StreamReader(txtScrollItemsPath.Text)

        Dim Writer As New StreamWriter(".\Data\item_multi_returns.xml")


        '\\ If the XML Document has been altered with Unknown Nodes or Attributes, handles them with the
        '   UnknownNode and UnknownAttribute events.
        '==============================================================================================
        AddHandler ScrollsSerializer.UnknownNode, AddressOf Serializer_UnknownNode
        AddHandler ScrollsSerializer.UnknownAttribute, AddressOf Serializer_UnknownAttribute

        AddHandler ScrollWriterSerializer.UnknownNode, AddressOf Serializer_UnknownNode
        AddHandler ScrollWriterSerializer.UnknownAttribute, AddressOf Serializer_UnknownAttribute

        AddHandler StringsSerializer.UnknownNode, AddressOf Serializer_UnknownNode
        AddHandler StringsSerializer.UnknownAttribute, AddressOf Serializer_UnknownAttribute

        '\\ Deserializes the specified XML Doc.
        '======================================
        txtLog.Text &= Now & "   " & "Loading  client_strings_item3.xml" & vbCrLf
        Dim ClientStrings3 As strings = CType(StringsSerializer.Deserialize(StringsReader), strings)

        txtLog.Text &= Now & "   " & "Loading  client_item_multi_return.xml" & vbCrLf
        Dim Out As Multi_Return_Scrolls = CType(ScrollsSerializer.Deserialize(ScrollsReader), Multi_Return_Scrolls)


        '\\ Serialized the specified object and writes into XML Doc  [Way #2]
        '=====================================================================
        Dim imr As New item_multi_returns

        For Each s In Out.Scrolls

            Dim mri As New MultiReturnsItems
            mri.id = s.id
            mri.name = s.name

            For Each d In s.return_loc_list.data

                Dim wd As New WorldData
                wd.worldid = d.return_worldid
                wd.desc = ClientStrings3.GetStringBodybyName(d.return_desc)

                mri.Loc.Add(wd)

            Next

            imr.items.Add(mri)

        Next

        ScrollWriterSerializer.Serialize(Writer, imr)


        ''\\ Starts to Parsing [Way #1]
        ''=============================
        'Dim strBuilder As New StringBuilder

        'strBuilder.AppendLine("<?xml version=""1.0"" encoding=""UTF-8"" standalone=""yes""?>")
        'strBuilder.AppendLine("<item_multi_returns xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"">")


        'For Each s In Out.Scrolls

        '    strBuilder.AppendLine(ControlChars.Tab & "<item " & "id=""" & s.id & """" & " name=""" & s.name & """>")

        '    For Each L In s.return_loc_list.data

        '        Dim WorldName As String = Nothing

        '        '\\ Finds Real World Name.
        '        '==========================
        '        WorldName = ClientStrings3.GetStringBodybyName(L.return_desc)

        '        strBuilder.AppendLine(ControlChars.Tab & ControlChars.Tab & "<loc " & "worldid=""" & L.return_worldid & """" & " desc=""" & WorldName & """>")

        '    Next

        '    strBuilder.AppendLine(ControlChars.Tab & "</item>")
        'Next

        'strBuilder.Append("</item_multi_returns>")

        'Writer.WriteLine(strBuilder.ToString)


        txtLog.Text &= Now & "   " & "Parsing Finished." & vbCrLf
        StringsReader.Close()
        ScrollsReader.Close()
        Writer.Close()

    End Sub



    Protected Sub Serializer_UnknownNode(sender As Object, e As XmlNodeEventArgs)
        txtLog.Text = "Unknown Node:" & e.Name & ControlChars.Tab & e.Text
    End Sub

    Protected Sub Serializer_UnknownAttribute(sender As Object, e As XmlAttributeEventArgs)
        Dim attr As XmlAttribute = e.Attr
        txtLog.Text = "Unknown Attribute: " & attr.Name & "='" & attr.Value & "'"
    End Sub


    Private Sub btnBrowserScrollItems_Click(sender As Object, e As EventArgs) Handles btnBrowserScrollItems.Click
        If OpenDlgScrolls.ShowDialog() = Windows.Forms.DialogResult.OK Then
            txtScrollItemsPath.Text = OpenDlgScrolls.FileName
        End If
    End Sub

    Private Sub btnBrowserStrItems3_Click(sender As Object, e As EventArgs) Handles btnBrowserStrItems3.Click
        If OpenDlgStringsItem3.ShowDialog() = Windows.Forms.DialogResult.OK Then
            txtStrItem3Path.Text = OpenDlgStringsItem3.FileName
        End If
    End Sub

    Private Sub lnkAionCore_LinkClicked(sender As Object, e As LinkLabelLinkClickedEventArgs) Handles lnkAionCore.LinkClicked
        System.Diagnostics.Process.Start("http://www.aion-core.net")
    End Sub

End Class





#Region "client_strings_item3 - XML DOC"

Public Class strings

    <XmlAttribute> _
    Public generated_time As String

    <XmlElement("string")> _
    Public str() As ItemString

    Public Function GetStringBodybyName(ByVal name As String) As String

        If Not (str Is Nothing) Then
            For Each n In str
                If (name = n.name) Then Return n.body
            Next
        End If

        Return ""

    End Function

    'Public Sub New()
    '    Me.generated_time = DateTime.Now
    'End Sub

End Class

Public Class ItemString

    Public id As String
    Public name As String
    Public body As String

End Class


#End Region


#Region "client_item_multi_returns - Serializing"

<XmlRoot("item_multi_returns")> _
Public Class item_multi_returns

    <XmlElement("item")> _
    Public items As New List(Of MultiReturnsItems)

End Class

Public Class MultiReturnsItems

    <XmlAttribute> Public id As String
    <XmlAttribute> Public name As String

    <XmlElement("loc")> _
    Public Loc As New List(Of WorldData)

End Class


Public Class WorldData

    <XmlAttribute> Public worldid As String
    <XmlAttribute> Public desc As String

End Class


#End Region


#Region "client_item_multi_returns - Deserializing"

<XmlRootAttribute("client_item_multi_returns", IsNullable:=False)> _
Public Class Multi_Return_Scrolls

    <XmlAttribute> _
    Public generated_time As String

    <XmlElementAttribute("client_item_multi_return")> _
    Public Scrolls() As Scroll

End Class

Public Class Scroll

    Public id As Integer
    Public name As String

    Public return_loc_list As LocationList

End Class

Public Class LocationList

    <XmlElementAttribute("data")> _
    Public data() As LocationData

End Class

Public Class LocationData

    Public return_alias As String
    Public return_worldid As String
    Public return_desc As String

End Class

#End Region

