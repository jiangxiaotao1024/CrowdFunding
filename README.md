# 本是一个众筹项目
# 开发工具eclipse,tomcat,mysql
# 开发技术spring,springmvc,mybatis,activiti5,jquery等
# 登入模块
登入/退出：根据所选登入类型到响应的表中查询账号密码信息，若验证通过跳转到管理员/会员页面并将用户信息放入session域。退出时清除session域<br>
登入拦截：配置登入拦截器。在拦截器中设置需要拦截的uri，当用户通过前端控制器访问时，不在拦截范围内放行，在拦截范围内判断用户是否登入若没有登入重定向到登入页面<br>
权限拦截：配置权限拦截器。配置一个监听器在项目启动时从数据库权限表获取所有权限对应的allUri放入session域中，当用户通过前端控制器访问时，获取访问路径uri，如果uri不在allUri内，再从数据库获取该用户访问权限userUris,如果uri不在userUri内打回登入界面，否则放行<br>
# 利用activiti5部署流程
通过监听器在服务器启动时创建流程引擎
通过ioc容器获取流程引擎
创建bpmn流程图，部署流程定义
# 会员模块
申请实名认证：查询ticket（记录审核状态，步骤），如果为空生成一条数据，如果不为空获取步骤信息跳转到该步骤。
账户类型选择：选择账户类型，更新用户信息及步骤信息。
实名认证：输入个人信息，更新用户信息及步骤。
资质文件上传：查询所选类型需要的资质，生成不同的资质上传表单，上传资质。生成资质信息，更新步骤。
输入邮箱：输入邮箱地址，更新用户信息，更新步骤。生成随机验证码，创建带（邮箱，验证码，用户信息，通过监听器，拒绝监听器）的流程实例，进入发送邮件任务自动发送验证码到用户邮箱。
输入验证码：输入验证码进行验证码校验，如果正确领取审核验证码任务，完成审核验证码任务。更新用户信息及步骤信息。
# 管理员模块
权限管理：
用户维护：实现管理员的增删查改，以及分配角色<br>
角色维护：实现角色的增删查改，以及分配权限<br>
许可维护：用ztree实现多级权限的增删查改<br>
业务管理：
资质维护：资质增删改查
分类管理：对不同的会员类型分类不同的资质
实名认证审核模块：
查询所有分配的所有任务，展现出来。
对某一任务进行审核，设置用户变量，通过设置flag变量为true完成任务，拒绝设置flag变量为false完成任务。
更具flag的值触发相应的监听器，更新用户信息及步骤<br>
# 用户认证模块
用activiti5创建工作流，在每一阶段记录状态，重新打开认证时继续流程<br>
在用户申请资质认证时，跳转到用户类型选择界面<br>
用户类型选择成功跳转到用户信息填写界面<br>
信息填写成功跳转到资质文件上传界面，根据所选用户类型弹出所需图片的上传框<br>
资质文件上传成功跳转到填写接收二维码的邮箱界面<br>
邮箱填写成功，开启发送验证码任务，会由email任务自动发送验证码到接收邮箱，并跳转到填写验证码界面<br>
验证码填写成功，开启验证码校验任务，验证码正确返回用户首页<br>
管理员进入实名认证什么模块，点击该审核进行审核<br>
提交审核进入审核任务，更具通过和不通过进入响应的监听器完成响应任务，审核结束<br>
