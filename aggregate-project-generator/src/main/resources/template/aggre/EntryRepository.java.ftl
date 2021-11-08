package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${entityPkgName}.${aggregate?cap_first}Entry;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ${classInfo.classComment} 持久化仓库
 * @author ${author}
 * @date ${date}
 */
@Repository
public interface ${aggregate?cap_first}EntryRepository extends
    JpaRepository<${aggregate?cap_first}Entry, ${classInfo.key.fieldClass}>
    /**QuerydslPredicateExecutor<${aggregate?cap_first}Entry>*/ {

}
