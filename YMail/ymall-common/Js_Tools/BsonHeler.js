/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

let bsonSourceStr = '{"obj": {"_id": 12345667777, "cname": {"$in": ["zhangsan"]}, "createTime": {"$gte": {"$date": "2023-12-23T09:50:44.34Z"}, "$lte": {"$date": "2023-12-31T09:50:44.34Z"}}}, "_id": 12345667777, "cname": {"$in": ["zhangsan"]}, "createTime": {"$gte": {"$date": "2023-12-23T09:50:44.34Z"}, "$lte": {"$date": "2023-12-31T09:50:44.34Z"}}}'

let bsonObj = JSON.parse(bsonSourceStr);

childExec(bsonObj, null, null);
let result = JSON.stringify(bsonObj, null, 4);
let regex = "\"new Date(.*)\"";
let regexObj = new RegExp(regex, "gm");

result = result


result = result.replace(regexObj, "new Date$1")
console.log("替换后: ", result)



function childExec(jsonObj, preJsonObj, preKey) {
    for (let bsonObjKey in jsonObj) {
        let bsonObjElement = jsonObj[bsonObjKey];
        if (bsonObjElement instanceof Object) {
            childExec(bsonObjElement, jsonObj, bsonObjKey)
        } else {
            if (bsonObjKey == '$date') {
                let jsonObjElement = jsonObj[bsonObjKey];
                console.log("上次key: ", jsonObjElement[preKey]);
                preJsonObj[preKey] = "new Date('" + jsonObjElement + "')";
            }
        }
    }
}