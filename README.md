# spring-boot-starter-jdbc-helper
支持jdk版本为1.8或者1.8+  
spring-boot-starter-jdbc-helper 是在springboot && springJDBC 基础上只做增强不做改变，为简化开发、提高效率而生。
### 如何使用

* 添加依赖:

```xml
<dependency>
    <groupId>com.github.nyvi</groupId>
    <artifactId>spring-boot-starter-jdbc-helper</artifactId>
    <version>1.1.1</version>
</dependency>
```
* 创建DO
```java
@Table("sys_user")
@SuppressWarnings("serial")
public class SysUserDO implements Serializable {

	/**
	 * id
	 */
	@Id
	private Long id;

	/**
	 * 用户名
	 */
	@Column
	private String username;

	/**
	 * 创建时间
	 */
	@Column(update = false)
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	@Column
	private Date gmtModified;
  
	//...
}
```
* 创建DAO
```java
@Repository
public class SysUserDAO extends BaseDAO<SysUserDO> {

}
```
* 创建Service
```java
public interface SysUserService extends BaseService<SysUserDO> {

}
```
* 创建ServiceImpl
```java
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDO> implements SysUserService {

}
```
* 创建Query
```java
@SuppressWarnings("serial")
public class SysUserQuery extends SysUserDO {

	@Query(operate = Operate.LIKE, suffix = "%")
	public String username;
  	
	//...
}
```


