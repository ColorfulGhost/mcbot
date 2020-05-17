package cc.vimc.mcbot.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Version;

public class BcryptHasher  {

    private static final int COST_FACTOR = 6;
//    private final


    public static String hash(String rawPassword) {
        BCrypt.Hasher hashAlg = BCrypt.with(Version.VERSION_2Y);
        //generate a different salt for each user
        return hashAlg.hashToString(COST_FACTOR, rawPassword.toCharArray());
    }

    public static boolean checkPassword(String passwordHash, String userInput) {
        if (passwordHash == null || passwordHash.isEmpty()) {
            return false;
        }

        return BCrypt.verifyer().verify(userInput.toCharArray(), passwordHash).verified;
    }
}