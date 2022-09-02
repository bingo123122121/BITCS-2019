
namespace homework3
{
    partial class Form1
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.Label 姓名Label;
            System.Windows.Forms.Label 学号Label;
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            this.提交button = new System.Windows.Forms.Button();
            this.可选课程label = new System.Windows.Forms.Label();
            this.已选课程label = new System.Windows.Forms.Label();
            this.已选课程数TextBox = new System.Windows.Forms.TextBox();
            this.已选课程数 = new System.Windows.Forms.Label();
            this.已选学分TextBox = new System.Windows.Forms.TextBox();
            this.已选学分 = new System.Windows.Forms.Label();
            this.姓名TextBox = new System.Windows.Forms.TextBox();
            this.学生BindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.dataSet31 = new homework3.DataSet3();
            this.学号TextBox = new System.Windows.Forms.TextBox();
            this.oleDbSelectCommand1 = new System.Data.OleDb.OleDbCommand();
            this.oleDbConnection1 = new System.Data.OleDb.OleDbConnection();
            this.oleDbDataAdapter1 = new System.Data.OleDb.OleDbDataAdapter();
            this.oleDbSelectCommand2 = new System.Data.OleDb.OleDbCommand();
            this.oleDbDataAdapter2 = new System.Data.OleDb.OleDbDataAdapter();
            this.oleDbSelectCommand3 = new System.Data.OleDb.OleDbCommand();
            this.oleDbDataAdapter3 = new System.Data.OleDb.OleDbDataAdapter();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.授课号DataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.课程名称DataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.学分DataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.授课教师DataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.教室位置DataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.授课时间DataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.退课 = new System.Windows.Forms.DataGridViewButtonColumn();
            this.dataSet11BindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.dataSet11 = new homework3.DataSet1();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.授课号DataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.课程名称DataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.学分DataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.授课教师DataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.教室位置DataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.授课时间DataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.选课dataGridView3 = new System.Windows.Forms.DataGridViewButtonColumn();
            this.dataSet21BindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.dataSet21 = new homework3.DataSet2();
            this.手工录入课程信息label = new System.Windows.Forms.Label();
            this.dataGridView3 = new System.Windows.Forms.DataGridView();
            this.教师职工号 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.课程号 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.教室号 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.授课时间 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.课程容量 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.选课_dataGridView3 = new System.Windows.Forms.DataGridViewButtonColumn();
            姓名Label = new System.Windows.Forms.Label();
            学号Label = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.学生BindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet31)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet11BindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet11)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet21BindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet21)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView3)).BeginInit();
            this.SuspendLayout();
            // 
            // 姓名Label
            // 
            姓名Label.AutoSize = true;
            姓名Label.Location = new System.Drawing.Point(301, 63);
            姓名Label.Name = "姓名Label";
            姓名Label.Size = new System.Drawing.Size(45, 15);
            姓名Label.TabIndex = 17;
            姓名Label.Text = "姓名:";
            // 
            // 学号Label
            // 
            学号Label.AutoSize = true;
            学号Label.Location = new System.Drawing.Point(83, 63);
            学号Label.Name = "学号Label";
            学号Label.Size = new System.Drawing.Size(45, 15);
            学号Label.TabIndex = 15;
            学号Label.Text = "学号:";
            // 
            // 提交button
            // 
            this.提交button.Location = new System.Drawing.Point(567, 680);
            this.提交button.Name = "提交button";
            this.提交button.Size = new System.Drawing.Size(75, 31);
            this.提交button.TabIndex = 28;
            this.提交button.Text = "提交";
            this.提交button.UseVisualStyleBackColor = true;
            this.提交button.Click += new System.EventHandler(this.提交button_Click);
            // 
            // 可选课程label
            // 
            this.可选课程label.AutoSize = true;
            this.可选课程label.Location = new System.Drawing.Point(83, 458);
            this.可选课程label.Name = "可选课程label";
            this.可选课程label.Size = new System.Drawing.Size(82, 15);
            this.可选课程label.TabIndex = 26;
            this.可选课程label.Text = "可选课程：";
            // 
            // 已选课程label
            // 
            this.已选课程label.AutoSize = true;
            this.已选课程label.Location = new System.Drawing.Point(83, 111);
            this.已选课程label.Name = "已选课程label";
            this.已选课程label.Size = new System.Drawing.Size(82, 15);
            this.已选课程label.TabIndex = 24;
            this.已选课程label.Text = "已选课程：";
            // 
            // 已选课程数TextBox
            // 
            this.已选课程数TextBox.Enabled = false;
            this.已选课程数TextBox.Location = new System.Drawing.Point(864, 60);
            this.已选课程数TextBox.Name = "已选课程数TextBox";
            this.已选课程数TextBox.Size = new System.Drawing.Size(100, 25);
            this.已选课程数TextBox.TabIndex = 22;
            // 
            // 已选课程数
            // 
            this.已选课程数.AutoSize = true;
            this.已选课程数.Location = new System.Drawing.Point(770, 63);
            this.已选课程数.Name = "已选课程数";
            this.已选课程数.Size = new System.Drawing.Size(97, 15);
            this.已选课程数.TabIndex = 21;
            this.已选课程数.Text = "已选课程数：";
            // 
            // 已选学分TextBox
            // 
            this.已选学分TextBox.Enabled = false;
            this.已选学分TextBox.Location = new System.Drawing.Point(602, 60);
            this.已选学分TextBox.Name = "已选学分TextBox";
            this.已选学分TextBox.Size = new System.Drawing.Size(100, 25);
            this.已选学分TextBox.TabIndex = 20;
            // 
            // 已选学分
            // 
            this.已选学分.AutoSize = true;
            this.已选学分.Location = new System.Drawing.Point(523, 63);
            this.已选学分.Name = "已选学分";
            this.已选学分.Size = new System.Drawing.Size(82, 15);
            this.已选学分.TabIndex = 19;
            this.已选学分.Text = "已选学分：";
            // 
            // 姓名TextBox
            // 
            this.姓名TextBox.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.学生BindingSource, "姓名", true));
            this.姓名TextBox.Enabled = false;
            this.姓名TextBox.Location = new System.Drawing.Point(352, 60);
            this.姓名TextBox.Name = "姓名TextBox";
            this.姓名TextBox.Size = new System.Drawing.Size(100, 25);
            this.姓名TextBox.TabIndex = 18;
            // 
            // 学生BindingSource
            // 
            this.学生BindingSource.DataMember = "学生";
            this.学生BindingSource.DataSource = this.dataSet31;
            // 
            // dataSet31
            // 
            this.dataSet31.DataSetName = "DataSet3";
            this.dataSet31.SchemaSerializationMode = System.Data.SchemaSerializationMode.IncludeSchema;
            // 
            // 学号TextBox
            // 
            this.学号TextBox.Location = new System.Drawing.Point(134, 60);
            this.学号TextBox.Name = "学号TextBox";
            this.学号TextBox.Size = new System.Drawing.Size(100, 25);
            this.学号TextBox.TabIndex = 16;
            this.学号TextBox.TextChanged += new System.EventHandler(this.学号TextBox_TextChanged_1);
            // 
            // oleDbSelectCommand1
            // 
            this.oleDbSelectCommand1.CommandText = resources.GetString("oleDbSelectCommand1.CommandText");
            this.oleDbSelectCommand1.Connection = this.oleDbConnection1;
            // 
            // oleDbConnection1
            // 
            this.oleDbConnection1.ConnectionString = "Provider=SQLNCLI11;Data Source=(local);Integrated Security=SSPI;Initial Catalog=选" +
    "课管理";
            this.oleDbConnection1.InfoMessage += new System.Data.OleDb.OleDbInfoMessageEventHandler(this.oleDbConnection1_InfoMessage);
            // 
            // oleDbDataAdapter1
            // 
            this.oleDbDataAdapter1.SelectCommand = this.oleDbSelectCommand1;
            this.oleDbDataAdapter1.TableMappings.AddRange(new System.Data.Common.DataTableMapping[] {
            new System.Data.Common.DataTableMapping("Table", "教师", new System.Data.Common.DataColumnMapping[] {
                        new System.Data.Common.DataColumnMapping("授课号", "授课号"),
                        new System.Data.Common.DataColumnMapping("课程名称", "课程名称"),
                        new System.Data.Common.DataColumnMapping("学分", "学分"),
                        new System.Data.Common.DataColumnMapping("授课教师", "授课教师"),
                        new System.Data.Common.DataColumnMapping("教室位置", "教室位置"),
                        new System.Data.Common.DataColumnMapping("授课时间", "授课时间")})});
            this.oleDbDataAdapter1.RowUpdated += new System.Data.OleDb.OleDbRowUpdatedEventHandler(this.oleDbDataAdapter1_RowUpdated);
            // 
            // oleDbSelectCommand2
            // 
            this.oleDbSelectCommand2.CommandText = resources.GetString("oleDbSelectCommand2.CommandText");
            this.oleDbSelectCommand2.Connection = this.oleDbConnection1;
            // 
            // oleDbDataAdapter2
            // 
            this.oleDbDataAdapter2.SelectCommand = this.oleDbSelectCommand2;
            this.oleDbDataAdapter2.TableMappings.AddRange(new System.Data.Common.DataTableMapping[] {
            new System.Data.Common.DataTableMapping("Table", "授课", new System.Data.Common.DataColumnMapping[] {
                        new System.Data.Common.DataColumnMapping("授课号", "授课号"),
                        new System.Data.Common.DataColumnMapping("课程名称", "课程名称"),
                        new System.Data.Common.DataColumnMapping("学分", "学分"),
                        new System.Data.Common.DataColumnMapping("授课教师", "授课教师"),
                        new System.Data.Common.DataColumnMapping("教室位置", "教室位置"),
                        new System.Data.Common.DataColumnMapping("授课时间", "授课时间")})});
            this.oleDbDataAdapter2.RowUpdated += new System.Data.OleDb.OleDbRowUpdatedEventHandler(this.oleDbDataAdapter2_RowUpdated);
            // 
            // oleDbSelectCommand3
            // 
            this.oleDbSelectCommand3.CommandText = "SELECT  姓名\r\nFROM      学生";
            this.oleDbSelectCommand3.Connection = this.oleDbConnection1;
            // 
            // oleDbDataAdapter3
            // 
            this.oleDbDataAdapter3.SelectCommand = this.oleDbSelectCommand3;
            this.oleDbDataAdapter3.TableMappings.AddRange(new System.Data.Common.DataTableMapping[] {
            new System.Data.Common.DataTableMapping("Table", "学生", new System.Data.Common.DataColumnMapping[] {
                        new System.Data.Common.DataColumnMapping("姓名", "姓名")})});
            // 
            // dataGridView1
            // 
            this.dataGridView1.AutoGenerateColumns = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.授课号DataGridViewTextBoxColumn,
            this.课程名称DataGridViewTextBoxColumn,
            this.学分DataGridViewTextBoxColumn,
            this.授课教师DataGridViewTextBoxColumn,
            this.教室位置DataGridViewTextBoxColumn,
            this.授课时间DataGridViewTextBoxColumn,
            this.退课});
            this.dataGridView1.DataMember = "教师";
            this.dataGridView1.DataSource = this.dataSet11BindingSource;
            this.dataGridView1.Location = new System.Drawing.Point(86, 129);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 51;
            this.dataGridView1.RowTemplate.Height = 27;
            this.dataGridView1.Size = new System.Drawing.Size(1030, 182);
            this.dataGridView1.TabIndex = 31;
            this.dataGridView1.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);
            // 
            // 授课号DataGridViewTextBoxColumn
            // 
            this.授课号DataGridViewTextBoxColumn.DataPropertyName = "授课号";
            this.授课号DataGridViewTextBoxColumn.HeaderText = "授课号";
            this.授课号DataGridViewTextBoxColumn.MinimumWidth = 6;
            this.授课号DataGridViewTextBoxColumn.Name = "授课号DataGridViewTextBoxColumn";
            this.授课号DataGridViewTextBoxColumn.ReadOnly = true;
            this.授课号DataGridViewTextBoxColumn.Width = 125;
            // 
            // 课程名称DataGridViewTextBoxColumn
            // 
            this.课程名称DataGridViewTextBoxColumn.DataPropertyName = "课程名称";
            this.课程名称DataGridViewTextBoxColumn.HeaderText = "课程名称";
            this.课程名称DataGridViewTextBoxColumn.MinimumWidth = 6;
            this.课程名称DataGridViewTextBoxColumn.Name = "课程名称DataGridViewTextBoxColumn";
            this.课程名称DataGridViewTextBoxColumn.ReadOnly = true;
            this.课程名称DataGridViewTextBoxColumn.Width = 125;
            // 
            // 学分DataGridViewTextBoxColumn
            // 
            this.学分DataGridViewTextBoxColumn.DataPropertyName = "学分";
            this.学分DataGridViewTextBoxColumn.HeaderText = "学分";
            this.学分DataGridViewTextBoxColumn.MinimumWidth = 6;
            this.学分DataGridViewTextBoxColumn.Name = "学分DataGridViewTextBoxColumn";
            this.学分DataGridViewTextBoxColumn.ReadOnly = true;
            this.学分DataGridViewTextBoxColumn.Width = 125;
            // 
            // 授课教师DataGridViewTextBoxColumn
            // 
            this.授课教师DataGridViewTextBoxColumn.DataPropertyName = "授课教师";
            this.授课教师DataGridViewTextBoxColumn.HeaderText = "授课教师";
            this.授课教师DataGridViewTextBoxColumn.MinimumWidth = 6;
            this.授课教师DataGridViewTextBoxColumn.Name = "授课教师DataGridViewTextBoxColumn";
            this.授课教师DataGridViewTextBoxColumn.ReadOnly = true;
            this.授课教师DataGridViewTextBoxColumn.Width = 125;
            // 
            // 教室位置DataGridViewTextBoxColumn
            // 
            this.教室位置DataGridViewTextBoxColumn.DataPropertyName = "教室位置";
            this.教室位置DataGridViewTextBoxColumn.HeaderText = "教室位置";
            this.教室位置DataGridViewTextBoxColumn.MinimumWidth = 6;
            this.教室位置DataGridViewTextBoxColumn.Name = "教室位置DataGridViewTextBoxColumn";
            this.教室位置DataGridViewTextBoxColumn.ReadOnly = true;
            this.教室位置DataGridViewTextBoxColumn.Width = 125;
            // 
            // 授课时间DataGridViewTextBoxColumn
            // 
            this.授课时间DataGridViewTextBoxColumn.DataPropertyName = "授课时间";
            this.授课时间DataGridViewTextBoxColumn.HeaderText = "授课时间";
            this.授课时间DataGridViewTextBoxColumn.MinimumWidth = 6;
            this.授课时间DataGridViewTextBoxColumn.Name = "授课时间DataGridViewTextBoxColumn";
            this.授课时间DataGridViewTextBoxColumn.ReadOnly = true;
            this.授课时间DataGridViewTextBoxColumn.Width = 125;
            // 
            // 退课
            // 
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleCenter;
            dataGridViewCellStyle1.NullValue = "退课";
            this.退课.DefaultCellStyle = dataGridViewCellStyle1;
            this.退课.HeaderText = "退课";
            this.退课.MinimumWidth = 6;
            this.退课.Name = "退课";
            this.退课.Width = 125;
            // 
            // dataSet11BindingSource
            // 
            this.dataSet11BindingSource.DataSource = this.dataSet11;
            this.dataSet11BindingSource.Position = 0;
            // 
            // dataSet11
            // 
            this.dataSet11.DataSetName = "DataSet1";
            this.dataSet11.SchemaSerializationMode = System.Data.SchemaSerializationMode.IncludeSchema;
            // 
            // dataGridView2
            // 
            this.dataGridView2.AutoGenerateColumns = false;
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.授课号DataGridViewTextBoxColumn1,
            this.课程名称DataGridViewTextBoxColumn1,
            this.学分DataGridViewTextBoxColumn1,
            this.授课教师DataGridViewTextBoxColumn1,
            this.教室位置DataGridViewTextBoxColumn1,
            this.授课时间DataGridViewTextBoxColumn1,
            this.选课dataGridView3});
            this.dataGridView2.DataMember = "授课";
            this.dataGridView2.DataSource = this.dataSet21BindingSource;
            this.dataGridView2.Location = new System.Drawing.Point(86, 476);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.RowHeadersWidth = 51;
            this.dataGridView2.RowTemplate.Height = 27;
            this.dataGridView2.Size = new System.Drawing.Size(1030, 180);
            this.dataGridView2.TabIndex = 32;
            this.dataGridView2.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellContentClick);
            // 
            // 授课号DataGridViewTextBoxColumn1
            // 
            this.授课号DataGridViewTextBoxColumn1.DataPropertyName = "授课号";
            this.授课号DataGridViewTextBoxColumn1.HeaderText = "授课号";
            this.授课号DataGridViewTextBoxColumn1.MinimumWidth = 6;
            this.授课号DataGridViewTextBoxColumn1.Name = "授课号DataGridViewTextBoxColumn1";
            this.授课号DataGridViewTextBoxColumn1.Width = 125;
            // 
            // 课程名称DataGridViewTextBoxColumn1
            // 
            this.课程名称DataGridViewTextBoxColumn1.DataPropertyName = "课程名称";
            this.课程名称DataGridViewTextBoxColumn1.HeaderText = "课程名称";
            this.课程名称DataGridViewTextBoxColumn1.MinimumWidth = 6;
            this.课程名称DataGridViewTextBoxColumn1.Name = "课程名称DataGridViewTextBoxColumn1";
            this.课程名称DataGridViewTextBoxColumn1.Width = 125;
            // 
            // 学分DataGridViewTextBoxColumn1
            // 
            this.学分DataGridViewTextBoxColumn1.DataPropertyName = "学分";
            this.学分DataGridViewTextBoxColumn1.HeaderText = "学分";
            this.学分DataGridViewTextBoxColumn1.MinimumWidth = 6;
            this.学分DataGridViewTextBoxColumn1.Name = "学分DataGridViewTextBoxColumn1";
            this.学分DataGridViewTextBoxColumn1.Width = 125;
            // 
            // 授课教师DataGridViewTextBoxColumn1
            // 
            this.授课教师DataGridViewTextBoxColumn1.DataPropertyName = "授课教师";
            this.授课教师DataGridViewTextBoxColumn1.HeaderText = "授课教师";
            this.授课教师DataGridViewTextBoxColumn1.MinimumWidth = 6;
            this.授课教师DataGridViewTextBoxColumn1.Name = "授课教师DataGridViewTextBoxColumn1";
            this.授课教师DataGridViewTextBoxColumn1.Width = 125;
            // 
            // 教室位置DataGridViewTextBoxColumn1
            // 
            this.教室位置DataGridViewTextBoxColumn1.DataPropertyName = "教室位置";
            this.教室位置DataGridViewTextBoxColumn1.HeaderText = "教室位置";
            this.教室位置DataGridViewTextBoxColumn1.MinimumWidth = 6;
            this.教室位置DataGridViewTextBoxColumn1.Name = "教室位置DataGridViewTextBoxColumn1";
            this.教室位置DataGridViewTextBoxColumn1.Width = 125;
            // 
            // 授课时间DataGridViewTextBoxColumn1
            // 
            this.授课时间DataGridViewTextBoxColumn1.DataPropertyName = "授课时间";
            this.授课时间DataGridViewTextBoxColumn1.HeaderText = "授课时间";
            this.授课时间DataGridViewTextBoxColumn1.MinimumWidth = 6;
            this.授课时间DataGridViewTextBoxColumn1.Name = "授课时间DataGridViewTextBoxColumn1";
            this.授课时间DataGridViewTextBoxColumn1.Width = 125;
            // 
            // 选课dataGridView3
            // 
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleCenter;
            dataGridViewCellStyle2.NullValue = "选课";
            this.选课dataGridView3.DefaultCellStyle = dataGridViewCellStyle2;
            this.选课dataGridView3.HeaderText = "选课";
            this.选课dataGridView3.MinimumWidth = 6;
            this.选课dataGridView3.Name = "选课dataGridView3";
            this.选课dataGridView3.Width = 125;
            // 
            // dataSet21BindingSource
            // 
            this.dataSet21BindingSource.DataSource = this.dataSet21;
            this.dataSet21BindingSource.Position = 0;
            // 
            // dataSet21
            // 
            this.dataSet21.DataSetName = "DataSet2";
            this.dataSet21.SchemaSerializationMode = System.Data.SchemaSerializationMode.IncludeSchema;
            // 
            // 手工录入课程信息label
            // 
            this.手工录入课程信息label.AutoSize = true;
            this.手工录入课程信息label.Location = new System.Drawing.Point(83, 340);
            this.手工录入课程信息label.Name = "手工录入课程信息label";
            this.手工录入课程信息label.Size = new System.Drawing.Size(142, 15);
            this.手工录入课程信息label.TabIndex = 33;
            this.手工录入课程信息label.Text = "手工录入课程信息：";
            // 
            // dataGridView3
            // 
            this.dataGridView3.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView3.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.教师职工号,
            this.课程号,
            this.教室号,
            this.授课时间,
            this.课程容量,
            this.选课_dataGridView3});
            this.dataGridView3.Location = new System.Drawing.Point(86, 358);
            this.dataGridView3.Name = "dataGridView3";
            this.dataGridView3.RowHeadersWidth = 51;
            this.dataGridView3.RowTemplate.Height = 27;
            this.dataGridView3.Size = new System.Drawing.Size(1030, 77);
            this.dataGridView3.TabIndex = 34;
            this.dataGridView3.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView3_CellContentClick);
            // 
            // 教师职工号
            // 
            this.教师职工号.HeaderText = "教师职工号";
            this.教师职工号.MinimumWidth = 6;
            this.教师职工号.Name = "教师职工号";
            this.教师职工号.Width = 125;
            // 
            // 课程号
            // 
            this.课程号.HeaderText = "课程号";
            this.课程号.MinimumWidth = 6;
            this.课程号.Name = "课程号";
            this.课程号.Width = 125;
            // 
            // 教室号
            // 
            this.教室号.HeaderText = "教室号";
            this.教室号.MinimumWidth = 6;
            this.教室号.Name = "教室号";
            this.教室号.Width = 125;
            // 
            // 授课时间
            // 
            this.授课时间.HeaderText = "授课时间";
            this.授课时间.MinimumWidth = 6;
            this.授课时间.Name = "授课时间";
            this.授课时间.Width = 125;
            // 
            // 课程容量
            // 
            this.课程容量.HeaderText = "课程容量";
            this.课程容量.MinimumWidth = 6;
            this.课程容量.Name = "课程容量";
            this.课程容量.Width = 125;
            // 
            // 选课_dataGridView3
            // 
            dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleCenter;
            dataGridViewCellStyle3.NullValue = "选课";
            this.选课_dataGridView3.DefaultCellStyle = dataGridViewCellStyle3;
            this.选课_dataGridView3.HeaderText = "选课";
            this.选课_dataGridView3.MinimumWidth = 6;
            this.选课_dataGridView3.Name = "选课_dataGridView3";
            this.选课_dataGridView3.Width = 125;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1182, 753);
            this.Controls.Add(this.dataGridView3);
            this.Controls.Add(this.手工录入课程信息label);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.提交button);
            this.Controls.Add(this.可选课程label);
            this.Controls.Add(this.已选课程label);
            this.Controls.Add(this.已选课程数TextBox);
            this.Controls.Add(this.已选课程数);
            this.Controls.Add(this.已选学分TextBox);
            this.Controls.Add(this.已选学分);
            this.Controls.Add(姓名Label);
            this.Controls.Add(this.姓名TextBox);
            this.Controls.Add(学号Label);
            this.Controls.Add(this.学号TextBox);
            this.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.学生BindingSource, "姓名", true));
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.学生BindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet31)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet11BindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet11)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet21BindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataSet21)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView3)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button 提交button;
        private System.Windows.Forms.Label 可选课程label;
        private System.Windows.Forms.Label 已选课程label;
        private System.Windows.Forms.TextBox 已选课程数TextBox;
        private System.Windows.Forms.Label 已选课程数;
        private System.Windows.Forms.TextBox 已选学分TextBox;
        private System.Windows.Forms.Label 已选学分;
        private System.Windows.Forms.TextBox 姓名TextBox;
        private System.Windows.Forms.TextBox 学号TextBox;
        private System.Data.OleDb.OleDbCommand oleDbSelectCommand1;
        private System.Data.OleDb.OleDbConnection oleDbConnection1;
        private System.Data.OleDb.OleDbDataAdapter oleDbDataAdapter1;
        private System.Data.OleDb.OleDbCommand oleDbSelectCommand2;
        private System.Data.OleDb.OleDbDataAdapter oleDbDataAdapter2;
        private DataSet2 dataSet21;
        private System.Windows.Forms.BindingSource dataSet21BindingSource;
        private System.Windows.Forms.BindingSource dataSet11BindingSource;
        private DataSet1 dataSet11;
        private System.Data.OleDb.OleDbCommand oleDbSelectCommand3;
        private System.Data.OleDb.OleDbDataAdapter oleDbDataAdapter3;
        private DataSet3 dataSet31;
        private System.Windows.Forms.BindingSource 学生BindingSource;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.Label 手工录入课程信息label;
        private System.Windows.Forms.DataGridView dataGridView3;
        private System.Windows.Forms.DataGridViewTextBoxColumn 授课号DataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn 课程名称DataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn 学分DataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn 授课教师DataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn 教室位置DataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn 授课时间DataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewButtonColumn 退课;
        private System.Windows.Forms.DataGridViewTextBoxColumn 授课号DataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn 课程名称DataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn 学分DataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn 授课教师DataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn 教室位置DataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn 授课时间DataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewButtonColumn 选课dataGridView3;
        private System.Windows.Forms.DataGridViewTextBoxColumn 教师职工号;
        private System.Windows.Forms.DataGridViewTextBoxColumn 课程号;
        private System.Windows.Forms.DataGridViewTextBoxColumn 教室号;
        private System.Windows.Forms.DataGridViewTextBoxColumn 授课时间;
        private System.Windows.Forms.DataGridViewTextBoxColumn 课程容量;
        private System.Windows.Forms.DataGridViewButtonColumn 选课_dataGridView3;
    }
}

