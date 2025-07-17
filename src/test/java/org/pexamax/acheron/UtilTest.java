// Package and Imports
// Test command: ./mvnw test -Dtest=UtilTest

package org.pexamax.acheron;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// import org.pexamax.acheron.Util;

public class UtilTest {

    
    @Test // Test passed
    void hashTest() {
        String input = "Never Gonna Give You Up, Never Gonna Let You Down";
        String salt = "HkYDdC7FvVElPdq8"; 
        int hashLength = 32;
        assertEquals("$argon2i$v=19$m=65535,t=2,p=1$SGtZRGRDN0Z2VkVsUGRxOA$TsaPqDZd+YDYQKFXvR6SKxKYP3h+XDk+NCHK85/QQYA", Util.hashPassword(input, salt.getBytes(), hashLength).getEncoded());
        // If this passes, the 2 other hash methods should also pass.

    }

    @Test // Test passed
    void verificationTest() {
        String password = "Never Gonna Give You Up, Never Gonna Let You Down";
        String hash = Util.hashPassword(password);
        assertTrue(Util.verifyPassword(password, hash));
        assertFalse(Util.verifyPassword("Never Gonna Make You Cry, Never Gonna Say Goodbye", hash));
    }

    @Test 
    void encryptionTest() {
        String password = "Never Gonna Give You Up, Never Gonna Let You Down";
        String privateKey = "-----BEGIN OPENSSH PRIVATE KEY-----b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAlwAAAAdzc2gtcnNhAAAAAwEAAQAAAYEAzG9x7y8Wq9nUpLHl/b2w9HnqW+Q7M6KUwM4slqQ+0B4rG9pMTvQ3M/UsN2OywH1vz6kqJ6Pcdq+KgxfGr7pBrVhHz92Qk1lY1HlTCtHhp32aKvlkRy5msV6XlGUe+Dr3piI1hJv3rm+Mxql8j31A0QiqI5VqAyG4c4v7NcQJeMYI9c6B13/hkgGcsqmi7Ug2EZf1XEVZvIDa/RJKCNxPdwGJkXQ5nRJ9KmZKsWlSiqRX30LjpGZbp4VqKn+TmgIGKd65byHTnu93rM04HXn3+MItkQZo/fUO5TCBAV3xq8xFs7aC5bCdtL1z4F4v7tbxnl7Z9wx8u8+K2wVztQKuCMLk5aQ96XsY9eJrIQv7AAAAwQJU0DrMyV2Nq7SZQIb6YuXHw8vE6Zxl7T0J0BnUVg58z8/N9OxzwDUt9bApt4OYc8zKU6Qh/tlYQ7ZaO2/fSqajytmtVuXW0OnBfyx41dyKGxTdk6G3Hewhz3xyr9nmjE31v3Z+caBOj5zXU+54fwf6IBXqCZvU3R0JUQW3kA46YzOJwqWHHEdcYZc9U9RfkVUyKqLqExBA==-----END OPENSSH PRIVATE KEY-----";
        String encrypted = Util.encryptPrivateKey(password, privateKey);
        String decrypted = Util.decryptPrivateKey(password, encrypted);
        assertEquals(privateKey, decrypted);
    }
}

