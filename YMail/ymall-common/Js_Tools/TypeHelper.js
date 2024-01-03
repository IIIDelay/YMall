/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */
const TYPE = Object.freeze({
    NUMBER: '[object Number]',
    STRING: '[object String]',
    BOOLEAN: '[object Boolean]',
    UNDEFINED: '[object Undefined]',
    OBJECT: '[object Object]'
})

let typeFunc = ele => Object.prototype.toString.call(ele);

let s = typeFunc("str");

to
