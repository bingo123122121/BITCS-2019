using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.Sql;
using System.Data.OleDb;

namespace homework3
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void dataGrid1_Navigate(object sender, NavigateEventArgs ne)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            //this.oleDbDataAdapter1.Fill(this.dataSet11);
            //this.oleDbDataAdapter2.Fill(this.dataSet21);
        }

        private void oleDbDataAdapter1_RowUpdated(object sender, System.Data.OleDb.OleDbRowUpdatedEventArgs e)
        {

        }

        private void oleDbDataAdapter2_RowUpdated(object sender, System.Data.OleDb.OleDbRowUpdatedEventArgs e)
        {

        }


        private void 学号TextBox_TextChanged_1(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(学号TextBox.Text);


            oleDbDataAdapter1.SelectCommand.CommandText = "SELECT  授课.授课号, 课程.课程名称, 课程.学分, 教师.姓名 AS 授课教师, 教室.教室位置, 授课.授课时间 " +
                "FROM 教师 INNER JOIN " +
                "授课 ON 教师.职工号 = 授课.职工号 INNER JOIN " +
                "教室 ON 授课.教室号 = 教室.教室号 INNER JOIN " +
                "课程 ON 授课.课程号 = 课程.课程号 INNER JOIN " +
                "选课 ON 授课.授课号 = 选课.授课号 INNER JOIN 学生 ON 选课.学号 = 学生.学号 AND 选课.学号 = 学生.学号 AND " +
                "学生.学号 = " + id +
                " UNION " +
                "SELECT  授课_1.授课号, 课程_1.课程名称, 课程_1.学分, 教师_1.姓名 AS 授课教师, 教室_1.教室位置, 授课_1.授课时间 " +
                "FROM 教师 AS 教师_1 INNER JOIN " +
                "授课 AS 授课_1 ON 教师_1.职工号 = 授课_1.职工号 INNER JOIN " +
                "教室 AS 教室_1 ON 授课_1.教室号 = 教室_1.教室号 INNER JOIN " +
                "课程 AS 课程_1 ON 授课_1.课程号 = 课程_1.课程号 INNER JOIN " +
                "临时选课 ON 授课_1.授课号 = 临时选课.授课号 INNER JOIN " +
                "学生 AS 学生_1 ON 临时选课.学号 = 学生_1.学号 AND 临时选课.学号 = 学生_1.学号 AND " +
                "学生_1.学号 = " + id;
            oleDbDataAdapter1.SelectCommand.Parameters
            //oleDbDataAdapter1.SelectCommand.ExecuteReader();
            this.dataSet11.Clear();
            this.oleDbDataAdapter1.Fill(this.dataSet11);

            oleDbDataAdapter2.SelectCommand.CommandText = "SELECT  授课.授课号, 课程.课程名称, 课程.学分, 教师.姓名 AS 授课教师, 教室.教室位置, 授课.授课时间 " +
                "FROM 教师 INNER JOIN " +
                "授课 ON 教师.职工号 = 授课.职工号 INNER JOIN " +
                "教室 ON 授课.教室号 = 教室.教室号 INNER JOIN " +
                "课程 ON 授课.课程号 = 课程.课程号 INNER JOIN " +
                "学生分配教师 ON 教师.职工号 = 学生分配教师.职工号 INNER JOIN " +
                "学生 ON 学生分配教师.学号 = 学生.学号 AND 学生.学号 = " + id;
            //oleDbDataAdapter1.SelectCommand.ExecuteReader();
            this.dataSet21.Clear();
            this.oleDbDataAdapter2.Fill(this.dataSet21);

            oleDbDataAdapter3.SelectCommand.CommandText = "SELECT  姓名 FROM 学生 WHERE 学号 =" + id;
            //oleDbDataAdapter1.SelectCommand.ExecuteReader();
            this.dataSet31.Clear();
            this.oleDbDataAdapter3.Fill(this.dataSet31);

            if(dataSet31.Tables[0].Rows.Count > 0)
            {
                int creditSum = 0;
                int selectedCourseCnt = dataSet11.Tables[0].Rows.Count;
                for(int i = 0; i < dataSet11.Tables[0].Rows.Count; i++)
                {
                    creditSum += Convert.ToInt32(dataSet11.Tables[0].Rows[i][1]);
                }
                已选学分TextBox.Text = creditSum.ToString();
                已选课程数TextBox.Text = selectedCourseCnt.ToString();
            }
            else
            {
                已选学分TextBox.Text = "";
                已选课程数TextBox.Text = "";
            }

        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if(e.ColumnIndex == 6)
            {
                string dropCourseId = this.dataGridView1.Rows[e.RowIndex].Cells[0].Value.ToString();
                int credit = Convert.ToInt32(this.dataGridView1.Rows[e.RowIndex].Cells[2].Value.ToString());
                // DataGridViewRow dataRow = this.dataGridView1.Rows[e.RowIndex];
                string deleteCourseSql1 = "delete from 选课 where 学号 = " + 学号TextBox.Text + " and 授课号 = " + dropCourseId;
                string deleteCourseSql2 = "delete from 临时选课 where 学号 = " + 学号TextBox.Text + " and 授课号 = " + dropCourseId;
                OleDbCommand deleteCourse = new OleDbCommand(deleteCourseSql1, oleDbConnection1);
                this.oleDbConnection1.Open();
                deleteCourse.ExecuteNonQuery();
                deleteCourse.CommandText = deleteCourseSql2;
                deleteCourse.ExecuteNonQuery();
                this.oleDbConnection1.Close();

                // 刷新列表
                this.dataSet11.Clear();
                this.oleDbDataAdapter1.Fill(this.dataSet11);

                this.dataSet21.Clear();
                this.oleDbDataAdapter2.Fill(this.dataSet21);

                // 刷新学分
                int creditSum = Convert.ToInt32(已选学分TextBox.Text) - credit;
                int selectedCourseCnt = Convert.ToInt32(已选课程数TextBox.Text) - 1;
                已选学分TextBox.Text = creditSum.ToString();
                已选课程数TextBox.Text = selectedCourseCnt.ToString();

                // 提示退课成功
                Form2 deleteSuccessAlert = new Form2("退课成功！");
                deleteSuccessAlert.Show();

            }
        }

        private void dataGridView2_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 6)
            {
                string chooseCourseId = this.dataGridView2.Rows[e.RowIndex].Cells[0].Value.ToString();
                int credit = Convert.ToInt32(this.dataGridView2.Rows[e.RowIndex].Cells[2].Value.ToString());
                // DataGridViewRow dataRow = this.dataGridView2.Rows[e.RowIndex];

                if(this.dataGridView1.Rows.Count >= 6)
                {
                    Form2 chooseFailAlert = new Form2("已选修5门课，不可再选！");
                    chooseFailAlert.Show();
                }
                else
                {
                    int flag = 0;
                    for(int i = 0; i < this.dataGridView1.Rows.Count - 1; i++)
                    {
                        if(chooseCourseId == this.dataGridView1.Rows[i].Cells[0].Value.ToString())
                        {
                            flag = 1;
                            break;
                        }
                    }
                    if(flag == 1)
                    {
                        Form2 chooseFailAlert = new Form2("已选修过这门课！");
                        chooseFailAlert.Show();
                    }
                    else
                    {
                        string chooseCourseSql = "insert into 临时选课 values(" + 学号TextBox.Text + " ," + chooseCourseId + " ,null)";
                        OleDbCommand chooseCourse = new OleDbCommand(chooseCourseSql, oleDbConnection1);
                        this.oleDbConnection1.Open();
                        chooseCourse.ExecuteNonQuery();
                        this.oleDbConnection1.Close();

                        // 刷新列表
                        this.dataSet11.Clear();
                        this.oleDbDataAdapter1.Fill(this.dataSet11);

                        this.dataSet21.Clear();
                        this.oleDbDataAdapter2.Fill(this.dataSet21);

                        // 刷新学分
                        int creditSum = Convert.ToInt32(已选学分TextBox.Text) + credit;
                        int selectedCourseCnt = Convert.ToInt32(已选课程数TextBox.Text) + 1;
                        已选学分TextBox.Text = creditSum.ToString();
                        已选课程数TextBox.Text = selectedCourseCnt.ToString();

                        // 提示选课成功
                        Form2 chooseSuccessAlert = new Form2("选课成功！");
                        chooseSuccessAlert.Show();
                    }
                }
            }
        }

        private void 提交button_Click(object sender, EventArgs e)
        {
            int credit = Convert.ToInt32(已选学分TextBox.Text), cnt = Convert.ToInt32(已选课程数TextBox.Text);

            if(credit <= 12 && credit >= 8 && cnt <= 5 && cnt >= 3)
            {
                string moveCourseSql = "insert into 选课 select * from 临时选课 where 临时选课.学号 = " + 学号TextBox.Text;
                string deleteCourseSql = "delete from 临时选课 where 学号 = " + 学号TextBox.Text;
                OleDbCommand submitCourse = new OleDbCommand(moveCourseSql, oleDbConnection1);
                this.oleDbConnection1.Open();
                submitCourse.ExecuteNonQuery();
                submitCourse.CommandText = deleteCourseSql;
                submitCourse.ExecuteNonQuery();
                this.oleDbConnection1.Close();

                Form2 saveSuccessAlert = new Form2("提交成功");
                saveSuccessAlert.Show();
            }
            else
            {
                string error;
                if(credit < 8)
                {
                    if(cnt < 3)
                    {
                        error = "当前选课数小于3且总学分小于8！";
                    }
                    else if(cnt > 5)
                    {
                        error = "当前选课数大于5且总学分小于8！";
                    }
                    else
                    {
                        error = "当前总学分小于8！";
                    }
                }
                else if (credit > 12)
                {
                    if (cnt < 3)
                    {
                        error = "当前选课数小于3且总学分大于12！";
                    }
                    else if (cnt > 5)
                    {
                        error = "当前选课数大于5且总学分大于12！";
                    }
                    else
                    {
                        error = "当前总学分大于12！";
                    }
                }
                else
                {
                    if (cnt < 3)
                    {
                        error = "当前选课数小于3！";
                    }
                    else
                    {
                        error = "当前选课数大于5！";
                    }
                }
                Form2 errorAlert = new Form2(error);
                errorAlert.Show();
            }
        }

        private void dataGridView3_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if(e.ColumnIndex == 5)
            {
                if(this.dataGridView3.Rows[e.RowIndex].Cells[0] == null || 
                    this.dataGridView3.Rows[e.RowIndex].Cells[1] == null ||
                    this.dataGridView3.Rows[e.RowIndex].Cells[2] == null ||
                    this.dataGridView3.Rows[e.RowIndex].Cells[3] == null ||
                    this.dataGridView3.Rows[e.RowIndex].Cells[4] == null)
                {
                    Form2 manualSelectFailAlert = new Form2("添加课程失败！");
                    manualSelectFailAlert.Show();
                }
                else
                {
                    int teacherId = Convert.ToInt32(this.dataGridView3.Rows[e.RowIndex].Cells[0].Value.ToString());
                    int courseId = Convert.ToInt32(this.dataGridView3.Rows[e.RowIndex].Cells[1].Value.ToString());
                    int roomId = Convert.ToInt32(this.dataGridView3.Rows[e.RowIndex].Cells[2].Value.ToString());
                    string teachTime = this.dataGridView3.Rows[e.RowIndex].Cells[3].Value.ToString();
                    int volume = Convert.ToInt32(this.dataGridView3.Rows[e.RowIndex].Cells[4].Value.ToString());

                    DataSet teachIdDataSet = new DataSet();
                    string selectTeachIdSql = "select 授课号 from 授课 where 职工号 = " + teacherId +
                        " and 课程号 = " + courseId + " and 教室号 = " + roomId + " and 授课时间 = '" + teachTime + "' and 课程容量 = " + volume;
                    this.oleDbConnection1.Open();
                    OleDbCommand teachIdCommand = new OleDbCommand(selectTeachIdSql, this.oleDbConnection1);
                    OleDbDataAdapter teachIdAdapter = new OleDbDataAdapter(teachIdCommand);
                    teachIdAdapter.Fill(teachIdDataSet);
                    this.oleDbConnection1.Close();

                    if(teachIdDataSet.Tables[0].Rows.Count == 0)
                    {
                        Form2 manualSelectFailAlert = new Form2("不存在这门课程，添加课程失败！");
                        manualSelectFailAlert.Show();
                    }
                    else
                    {
                        int teachId = Convert.ToInt32(teachIdDataSet.Tables[0].Rows[0][0].ToString());

                        int flag = 0;
                        for (int i = 0; i < this.dataGridView1.Rows.Count - 1; i++)
                        {
                            if (teachId.ToString() == this.dataGridView1.Rows[i].Cells[0].Value.ToString())
                            {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 1)
                        {
                            Form2 chooseFailAlert = new Form2("已选修过这门课！");
                            chooseFailAlert.Show();
                        }
                        else
                        {
                            if (this.dataGridView1.Rows.Count >= 6)
                            {
                                Form2 chooseFailAlert = new Form2("已选修5门课，不可再选！");
                                chooseFailAlert.Show();
                            }
                            else
                            {
                                string manualSelectCourseSql = "insert into 选课 values(" + 学号TextBox.Text + " ," + teachId + " ,null)";
                                OleDbCommand manualSelectCourse = new OleDbCommand(manualSelectCourseSql, oleDbConnection1);
                                this.oleDbConnection1.Open();
                                manualSelectCourse.ExecuteNonQuery();
                                this.oleDbConnection1.Close();

                                // 刷新列表
                                this.dataSet11.Clear();
                                this.oleDbDataAdapter1.Fill(this.dataSet11);

                                this.dataSet21.Clear();
                                this.oleDbDataAdapter2.Fill(this.dataSet21);

                                // 刷新学分
                                int creditSum = Convert.ToInt32(已选学分TextBox.Text) + Convert.ToInt32(this.dataGridView2.Rows[e.RowIndex].Cells[2].Value.ToString());
                                int selectedCourseCnt = Convert.ToInt32(已选课程数TextBox.Text) + 1;
                                已选学分TextBox.Text = creditSum.ToString();
                                已选课程数TextBox.Text = selectedCourseCnt.ToString();

                                Form2 manualSelectSuccessAlert = new Form2("添加课程成功！");
                                manualSelectSuccessAlert.Show();

                                this.dataGridView3.Rows.Clear();
                            }
                            
                        }
                        


                    }


                    
                }
                

            }
            
        }

        private void oleDbConnection1_InfoMessage(object sender, OleDbInfoMessageEventArgs e)
        {

        }
    }
}
