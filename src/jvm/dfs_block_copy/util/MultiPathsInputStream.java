package dfs_block_copy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * InputStream abstraction over a list of hdfs files. Data is read from files in order.
 *
 * @author rdsr
 *
 */
public class MultiPathsInputStream extends InputStream {
    private final FileSystem _fs;
    private final List<Path> _files;

    private int _next;
    private InputStream _curIs;

    public MultiPathsInputStream(Configuration conf, List<Path> paths) throws IOException {
        _files = paths;
        _fs = FileSystem.get(conf);
        _next = 0;
    }

    @Override
    public int read() throws IOException {
        if (_curIs == null) {
            if (_next >= _files.size()) {
                return -1;
            } else {
                _curIs = _fs.open(_files.get(_next));
                _next += 1;
            }
        }

        final int b = _curIs.read();
        if (b == -1) {
            // close current stream
            close();
            // and start reading from next file
            return read();
        } else {
            return b;
        }
    }

    @Override
    public void close() throws IOException {
        if (_curIs != null) {
            _curIs.close();
            _curIs = null;
        }
    }
}
