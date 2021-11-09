package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${entityPkgName}.${aggregate?cap_first}Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * ${classInfo.classComment} 持久化仓库
 * @author ${author}
 * @date ${date}
 */
@Repository
public interface ${aggregate?cap_first}EntryRepository extends
    MongoRepository<${aggregate?cap_first}Entry, ${classInfo.key.fieldClass}>{

}
