//@ts-check
import { test, expect, chromium } from '@playwright/test';
import UserAgent from 'user-agents';
import 'dotenv/config';

test('authenticate user with custom context', async () => {

  const optionsBrowser = {
    headless: false,
    args: [
      '--disable-blink-features=AutomationControlled',
      '--no-sandbox',
      '--disable-web-security',
      '--disable-infobars',
      '--disable-extensions',
      '--start-maximized',
      '--window-size=1280,720',
    ],
  };

  const browser = await chromium.launch(optionsBrowser);

  const optionsContext = {
    userAgent: new UserAgent([/Chrome/i, { deviceCategory: 'desktop' }]).userAgent,
    locale: 'en-US',
    viewport: { width: 1280, height: 720 },
    deviceScaleFactor: 1,
    recordVideo: {
      dir: 'videos/',
      size: { width: 1280, height: 720 },
    },
  };

  const context = await browser.newContext(optionsContext);

  const page = await context.newPage();

  await page.goto('http://localhost:5173/welcome');
  await page.locator('div').nth(3).click();

  await page.getByRole('textbox', { name: 'Email or phone' }).fill(`${process.env.TEST_EMAIL}`);
  await page.getByRole('button', { name: 'Next' }).click();

  await page.getByRole('textbox', { name: 'Enter your password' }).fill(`${process.env.TEST_PASSWORD}`);
  await page.getByRole('button', { name: 'Next' }).click();
  await page.getByRole('button', { name: 'Continue' }).click();

  await page.waitForURL('**/home', { timeout: 30000 });
  await page.waitForTimeout(2000);

  await context.storageState({ path: 'myGearShareAuth.json' });

  await browser.close();
});
