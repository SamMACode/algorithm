# 学生表基本信息表pe_student.
create table pe_student (
  id_stu tinyint primary key auto_increment comment '学生表的主键',
  stu_name varchar(30) not null comment '学生的姓名',
  fk_grade_id tinyint not null comment '学生所在的年级id'
)engine=InnoDB default charset=utf8 comment='学生基本信息表';
# 学生所在年级的信息表pe_grade.
create table pe_grade (
  id_grade tinyint primary key auto_increment comment '学生所在的grade_id信息',
  grade_name varchar(50) not null comment '年级名称'
)engine=InnoDB default charset=utf8 comment='年级信息表';
# 选课信息表stu_select_course.
create table stu_select_course (
  id_sel tinyint primary key auto_increment comment '表示选课表的id',
  stu_id tinyint not null comment '学生的id编号',
  course_id tinyint not null comment '课程的id编号'
)engine=InnoDB default charset=utf8 comment '学生选课信息表';
# 课程的基本信息表pe_course.
create table pe_course(
  id_course tinyint primary key auto_increment comment '表示的是课程的id编号',
  cou_name varchar(50) not null comment '课程的名称'
)engine=InnoDB default charset=utf8 comment '课程基本信息表';

# 初始数据表中的数据.
insert into pe_student(stu_name, fk_grade_id)
values('karl', 2),('may', 2), ('matthews', 3);
# 插入年级表中的数据grade.
insert into pe_grade(grade_name) values('first'), ('second'), ('third');
# 网课程表course中插入数据.
insert into pe_course(cou_name)
values('english course'), ('math course'), ('computer science');
# 插入学生的选课情况.
insert into stu_select_course(stu_id, course_id)
values(3, 1), (3, 3);

# 1)按照(年级)查找所选(课程)的(人数).
### second	computer science	1
### second	english course	1
### first	math course	1
### first	english course	1
select gr.grade_name as '年级名称', cou.cou_name as '课程名称', count(*) as '选课人数'
from stu_select_course sel
  inner join pe_student stu on sel.stu_id = stu.id_stu
  inner join pe_grade gr on gr.id_grade = stu.fk_grade_id
  inner join pe_course cou on cou.id_course = sel.course_id
group by cou.cou_name, gr.grade_name;

# 2)找出各年级没有选课的学生人数(count).
### second	2
### third	2
select gr.grade_name as '年级名称', count(*) as '人数' from
  (select stu.fk_grade_id from pe_student stu left join stu_select_course sl
      on stu.id_stu = sl.stu_id
  where sl.course_id is null) rs inner join pe_grade gr on rs.fk_grade_id = gr.id_grade
group by gr.grade_name;

/**
 * 2.买鞋的问题：一双鞋20成本30卖出,其中有一客人买了一双鞋,给了50块钱.
 *    老板找不开找邻居换了5张10块,最后找给客人10块钱.
 *    隔壁老板最后发现50钱是假的,最后要还钱;(最后计算老板的损失?)
 */
### 老板的损失是40/50块钱.
客人入账: 一双鞋+20元真钱.
客人出账: 50元假钱(空手套白狼)
--> 账务是平衡的 <--
老板入账: 50元假钱
老板出账: 一双鞋(价格/或成本)+20元真钱

(换钱的部分):
老板: 50元假钱 <==> 5张10元真钱 <==> 找给客人20元+自己留下30元.
隔壁老板: 5张10元真钱 <==> 1张50元假钱
(隔壁老板发现假钱后):
老板: 30元真钱 + 20元真钱(自己补贴)  <==> 原来的50元假钱.
隔壁店老板: 5张10元假钱 <==> 1张50元真钱  <==> 再换回来
(从本质上说):
店老板收了50元假钱  <==> 卖了一双鞋+20元真钱(老板的损失).