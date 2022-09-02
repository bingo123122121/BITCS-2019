
namespace homework3
{
    partial class Form2
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.消息label = new System.Windows.Forms.Label();
            this.消息textBox = new System.Windows.Forms.TextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // 消息label
            // 
            this.消息label.AutoSize = true;
            this.消息label.Location = new System.Drawing.Point(364, 174);
            this.消息label.Name = "消息label";
            this.消息label.Size = new System.Drawing.Size(0, 15);
            this.消息label.TabIndex = 0;
            this.消息label.Click += new System.EventHandler(this.label1_Click);
            // 
            // 消息textBox
            // 
            this.消息textBox.Enabled = false;
            this.消息textBox.Location = new System.Drawing.Point(12, 51);
            this.消息textBox.Name = "消息textBox";
            this.消息textBox.Size = new System.Drawing.Size(258, 25);
            this.消息textBox.TabIndex = 1;
            this.消息textBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(100, 106);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 2;
            this.button1.Text = "确定";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(282, 153);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.消息textBox);
            this.Controls.Add(this.消息label);
            this.Name = "Form2";
            this.Text = "Form2";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label 消息label;
        private System.Windows.Forms.TextBox 消息textBox;
        private System.Windows.Forms.Button button1;
    }
}