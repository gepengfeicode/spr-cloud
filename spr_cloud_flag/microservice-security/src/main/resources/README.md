**本模块为Spring Boot 集成Spring Security模块实现权限管理**

### 特殊点

1.sql

在存储权限表的时候必须是以以下规则进行存储。

ROLE_*****

示例

```sql
insert into sys_permission(permissionName) values ('ROLE_permission_add');
```

2.注解

value值必须以hashRole('ROLE_开头的权限值')

示例:

```java
@PreAuthorize(value = "hasRole('ROLE_permission_add')")
```

