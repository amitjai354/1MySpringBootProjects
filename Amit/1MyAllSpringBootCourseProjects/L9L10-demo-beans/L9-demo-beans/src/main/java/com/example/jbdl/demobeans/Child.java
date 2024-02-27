package com.example.jbdl.demobeans;

public class Child extends Parent{
    @Override // retention policy is source so discarded by compiler does not go to class filea
    // as this annotation is only needed to check we are giving correct name of overridden method
    public void test(){}
}

//@NotNull  in @Valid-> retention policy is CLASS