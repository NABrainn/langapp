package org.langapp.users.controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.langapp.AppPaths;
import org.langapp.database.DataSource;
import org.langapp.translations.dto.Language;
import org.langapp.users.dto.Role;

import java.sql.SQLException;
import java.util.Objects;

public class UserController {
    public static void create(@NotNull Context context) {
        var username = Objects.requireNonNull(context.queryParam("username"));
        var password = Objects.requireNonNull(context.queryParam("password"));
        var role = Role.BASIC;
        var fromLang = Language.EN;
        var toLang = Language.NO;
        var uiLang = Language.EN;
        var sql = """
                    INSERT INTO public.users (
                        username,
                        password,
                        role,
                        from_lang,
                        to_lang,
                        ui_lang
                    )
                    VALUES (
                        ?,
                        ?,
                        ?,
                        ?,
                        ?,
                        ?
                    )
                    """;
        try(var con = DataSource.INSTANCE.connect();
            var ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setObject(3, role);
            ps.setObject(4, fromLang);
            ps.setObject(5, toLang);
            ps.setObject(6, uiLang);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            context.render(AppPaths.ERROR).status(500);
        }
    }
}
