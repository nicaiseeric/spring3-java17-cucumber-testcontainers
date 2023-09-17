package fr.neads.data.common.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.GZIPInputStream;

@Slf4j
@RequiredArgsConstructor
public class LargeGzippedTextFileReader implements Supplier<Stream<String>> {
    private final String cdrRawFilePathStr;

    @Override
    public Stream<String> get() {
        log.info("processing file [filename={}] ...", cdrRawFilePathStr);
        try(InputStream gzipStream = new GZIPInputStream(new FileInputStream(cdrRawFilePathStr))) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipStream));
            Stream<String> lines = StreamSupport.stream(reader.lines().spliterator(), false)
                    .onClose(
                            () -> {
                                try {
                                    reader.close();
                                    gzipStream.close();
                                } catch (IOException ioException){
                                    log.warn("could not close resources properly due to ...", ioException);
                                    throw new RuntimeException(ioException);
                                }
                            }
                    );
            return lines;
        } catch (IOException e) {
            log.debug("failed to read file {} due to ...", cdrRawFilePathStr, e);
        }
        return Stream.empty();
    }
}
