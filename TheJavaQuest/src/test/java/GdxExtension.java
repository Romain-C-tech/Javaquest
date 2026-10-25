import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.mockito.Mockito.mock;

public class GdxExtension implements BeforeAllCallback {

    private static boolean isInitialized = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!isInitialized) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

            new HeadlessApplication(new ApplicationListener() {
                @Override public void create() {}
                @Override public void resize(int width, int height) {}
                @Override public void render() {}
                @Override public void pause() {}
                @Override public void resume() {}
                @Override public void dispose() {}
            }, config);

            Gdx.gl = mock(GL20.class);
            Gdx.gl20 = mock(GL20.class);

            isInitialized = true;
        }
    }
}