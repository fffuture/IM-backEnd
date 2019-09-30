package com.xit.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Param;

//import com.xit.entity.Group;
import com.xit.entity.GroupRelation;

/*import org.apache.ibatis.annotations.Mapper;*/


/*@Mapper*/
public interface GroupMapper {
	
	public List<GroupRelation> queryMemberAll();

	public int queryGroupId();
	
	public void addMember(  @Param("groupId")int groupId, @Param("member")String member );
	
	public void createGroup( @Param("name")String name, @Param("creator")String creator );
	
}
