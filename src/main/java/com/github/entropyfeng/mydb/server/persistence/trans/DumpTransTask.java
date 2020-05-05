package com.github.entropyfeng.mydb.server.persistence.trans;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.domain.ValuesDomain;
import com.github.entropyfeng.mydb.server.persistence.PersistenceDomain;
import sun.net.www.http.ChunkedInputStream;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * @author entropyfeng
 */
public class DumpTransTask implements Callable<ValuesDomain> {

    private Collection<ProtoBuf.ResBody> resBodies;

    private File file;

    @Override
    public ValuesDomain call() throws Exception {

        PersistenceHelper.constructFile(resBodies,file);
        ValuesDomain valuesDomain= PersistenceHelper.load()
        return null;
    }
}
