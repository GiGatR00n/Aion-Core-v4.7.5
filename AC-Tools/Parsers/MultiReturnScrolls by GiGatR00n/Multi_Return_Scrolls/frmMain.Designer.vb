<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Partial Class frmMain
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()> _
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    <System.Diagnostics.DebuggerStepThrough()> _
    Private Sub InitializeComponent()
        Dim resources As System.ComponentModel.ComponentResourceManager = New System.ComponentModel.ComponentResourceManager(GetType(frmMain))
        Me.txtLog = New System.Windows.Forms.TextBox()
        Me.btnParser = New System.Windows.Forms.Button()
        Me.btnClose = New System.Windows.Forms.Button()
        Me.Label1 = New System.Windows.Forms.Label()
        Me.Label2 = New System.Windows.Forms.Label()
        Me.txtScrollItemsPath = New System.Windows.Forms.TextBox()
        Me.txtStrItem3Path = New System.Windows.Forms.TextBox()
        Me.btnBrowserScrollItems = New System.Windows.Forms.Button()
        Me.btnBrowserStrItems3 = New System.Windows.Forms.Button()
        Me.OpenDlgScrolls = New System.Windows.Forms.OpenFileDialog()
        Me.OpenDlgStringsItem3 = New System.Windows.Forms.OpenFileDialog()
        Me.lnkAionCore = New System.Windows.Forms.LinkLabel()
        Me.SuspendLayout()
        '
        'txtLog
        '
        Me.txtLog.BackColor = System.Drawing.Color.Silver
        Me.txtLog.BorderStyle = System.Windows.Forms.BorderStyle.None
        Me.txtLog.Enabled = False
        Me.txtLog.Font = New System.Drawing.Font("Consolas", 11.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(178, Byte))
        Me.txtLog.Location = New System.Drawing.Point(15, 67)
        Me.txtLog.Multiline = True
        Me.txtLog.Name = "txtLog"
        Me.txtLog.ScrollBars = System.Windows.Forms.ScrollBars.Vertical
        Me.txtLog.Size = New System.Drawing.Size(659, 225)
        Me.txtLog.TabIndex = 6
        '
        'btnParser
        '
        Me.btnParser.Font = New System.Drawing.Font("Microsoft Sans Serif", 10.0!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(178, Byte))
        Me.btnParser.Location = New System.Drawing.Point(12, 298)
        Me.btnParser.Name = "btnParser"
        Me.btnParser.Size = New System.Drawing.Size(128, 29)
        Me.btnParser.TabIndex = 7
        Me.btnParser.Text = "&Start"
        Me.btnParser.UseVisualStyleBackColor = True
        '
        'btnClose
        '
        Me.btnClose.Font = New System.Drawing.Font("Microsoft Sans Serif", 10.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(178, Byte))
        Me.btnClose.Location = New System.Drawing.Point(543, 298)
        Me.btnClose.Name = "btnClose"
        Me.btnClose.Size = New System.Drawing.Size(128, 29)
        Me.btnClose.TabIndex = 8
        Me.btnClose.Text = "&Close"
        Me.btnClose.UseVisualStyleBackColor = True
        '
        'Label1
        '
        Me.Label1.AutoSize = True
        Me.Label1.Location = New System.Drawing.Point(12, 13)
        Me.Label1.Name = "Label1"
        Me.Label1.Size = New System.Drawing.Size(178, 13)
        Me.Label1.TabIndex = 0
        Me.Label1.Text = "Path to  client_item_multi_return.xml:"
        '
        'Label2
        '
        Me.Label2.AutoSize = True
        Me.Label2.Location = New System.Drawing.Point(12, 39)
        Me.Label2.Name = "Label2"
        Me.Label2.Size = New System.Drawing.Size(160, 13)
        Me.Label2.TabIndex = 3
        Me.Label2.Text = "Path to  client_strings_item3.xml:"
        '
        'txtScrollItemsPath
        '
        Me.txtScrollItemsPath.Location = New System.Drawing.Point(195, 10)
        Me.txtScrollItemsPath.Name = "txtScrollItemsPath"
        Me.txtScrollItemsPath.Size = New System.Drawing.Size(426, 20)
        Me.txtScrollItemsPath.TabIndex = 1
        Me.txtScrollItemsPath.Text = ".\data\client_item_multi_return.xml"
        '
        'txtStrItem3Path
        '
        Me.txtStrItem3Path.Location = New System.Drawing.Point(195, 36)
        Me.txtStrItem3Path.Name = "txtStrItem3Path"
        Me.txtStrItem3Path.Size = New System.Drawing.Size(426, 20)
        Me.txtStrItem3Path.TabIndex = 4
        Me.txtStrItem3Path.Text = ".\data\client_strings_item3.xml"
        '
        'btnBrowserScrollItems
        '
        Me.btnBrowserScrollItems.Font = New System.Drawing.Font("Microsoft Sans Serif", 8.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(178, Byte))
        Me.btnBrowserScrollItems.Location = New System.Drawing.Point(627, 9)
        Me.btnBrowserScrollItems.Name = "btnBrowserScrollItems"
        Me.btnBrowserScrollItems.Size = New System.Drawing.Size(44, 21)
        Me.btnBrowserScrollItems.TabIndex = 2
        Me.btnBrowserScrollItems.Text = "..."
        Me.btnBrowserScrollItems.UseVisualStyleBackColor = True
        '
        'btnBrowserStrItems3
        '
        Me.btnBrowserStrItems3.Font = New System.Drawing.Font("Microsoft Sans Serif", 8.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(178, Byte))
        Me.btnBrowserStrItems3.Location = New System.Drawing.Point(627, 35)
        Me.btnBrowserStrItems3.Name = "btnBrowserStrItems3"
        Me.btnBrowserStrItems3.Size = New System.Drawing.Size(44, 21)
        Me.btnBrowserStrItems3.TabIndex = 5
        Me.btnBrowserStrItems3.Text = "..."
        Me.btnBrowserStrItems3.UseVisualStyleBackColor = True
        '
        'OpenDlgScrolls
        '
        Me.OpenDlgScrolls.FileName = "client_item_multi_return.xml"
        Me.OpenDlgScrolls.Filter = "XML files|*.xml"
        Me.OpenDlgScrolls.InitialDirectory = ".\data"
        Me.OpenDlgScrolls.RestoreDirectory = True
        Me.OpenDlgScrolls.Title = "Select file..."
        '
        'OpenDlgStringsItem3
        '
        Me.OpenDlgStringsItem3.FileName = "client_strings_item3.xml"
        Me.OpenDlgStringsItem3.Filter = "XML files|*.xml"
        Me.OpenDlgStringsItem3.InitialDirectory = ".\data"
        Me.OpenDlgStringsItem3.RestoreDirectory = True
        Me.OpenDlgStringsItem3.Title = "Select file..."
        '
        'lnkAionCore
        '
        Me.lnkAionCore.AutoSize = True
        Me.lnkAionCore.Location = New System.Drawing.Point(269, 306)
        Me.lnkAionCore.Name = "lnkAionCore"
        Me.lnkAionCore.Size = New System.Drawing.Size(129, 13)
        Me.lnkAionCore.TabIndex = 9
        Me.lnkAionCore.TabStop = True
        Me.lnkAionCore.Text = "Visit Aion-Core Community"
        '
        'frmMain
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(683, 331)
        Me.Controls.Add(Me.lnkAionCore)
        Me.Controls.Add(Me.btnBrowserStrItems3)
        Me.Controls.Add(Me.btnBrowserScrollItems)
        Me.Controls.Add(Me.txtStrItem3Path)
        Me.Controls.Add(Me.txtScrollItemsPath)
        Me.Controls.Add(Me.Label2)
        Me.Controls.Add(Me.Label1)
        Me.Controls.Add(Me.btnClose)
        Me.Controls.Add(Me.btnParser)
        Me.Controls.Add(Me.txtLog)
        Me.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle
        Me.Icon = CType(resources.GetObject("$this.Icon"), System.Drawing.Icon)
        Me.MaximizeBox = False
        Me.Name = "frmMain"
        Me.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen
        Me.Text = "Multi Return Scrolls Parser [by GiGatR00n v4.7.5.x]"
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub
    Friend WithEvents txtLog As System.Windows.Forms.TextBox
    Friend WithEvents btnParser As System.Windows.Forms.Button
    Friend WithEvents btnClose As System.Windows.Forms.Button
    Friend WithEvents Label1 As System.Windows.Forms.Label
    Friend WithEvents Label2 As System.Windows.Forms.Label
    Friend WithEvents txtScrollItemsPath As System.Windows.Forms.TextBox
    Friend WithEvents txtStrItem3Path As System.Windows.Forms.TextBox
    Friend WithEvents btnBrowserScrollItems As System.Windows.Forms.Button
    Friend WithEvents btnBrowserStrItems3 As System.Windows.Forms.Button
    Friend WithEvents OpenDlgScrolls As System.Windows.Forms.OpenFileDialog
    Friend WithEvents OpenDlgStringsItem3 As System.Windows.Forms.OpenFileDialog
    Friend WithEvents lnkAionCore As System.Windows.Forms.LinkLabel

End Class
