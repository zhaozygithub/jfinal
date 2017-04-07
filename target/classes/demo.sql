#namespace("zhao")
#sql("findUserById")
select * from user where id = ?
#end

#sql("findUserById2")
select * from user where id = #p(id)
#end

#end